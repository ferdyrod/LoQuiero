package com.ferdyrodriguez.data.remote

import com.ferdyrodriguez.data.dataSource.RemoteDataSource
import com.ferdyrodriguez.data.models.ApiResponse
import com.ferdyrodriguez.data.models.ErrorResponse
import com.ferdyrodriguez.data.models.ModelMapper
import com.ferdyrodriguez.data.models.dto.*
import com.ferdyrodriguez.domain.exceptions.Failure
import com.ferdyrodriguez.domain.exceptions.Failure.ServerError
import com.ferdyrodriguez.domain.fp.Either
import com.ferdyrodriguez.domain.fp.Either.Left
import com.ferdyrodriguez.domain.fp.Either.Right
import com.ferdyrodriguez.domain.models.AuthUser
import com.ferdyrodriguez.domain.models.Product
import com.ferdyrodriguez.domain.models.RegisterUser
import com.ferdyrodriguez.domain.models.UserProfile
import com.squareup.moshi.Moshi
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import java.io.File

class RemoteDataSourceImpl constructor(
    private val service: ApiService,
    private val mapper: ModelMapper
) : RemoteDataSource {


    override fun registerUser(email: String, password: String): Either<Failure, RegisterUser> {
        val call = service.registerUser(RegisterUserDto(email, password))
        return request(call, { mapper.RegisterUserToDomain(it) }, mapper.emptyRegisterUsed())
    }

    override fun verifyToken(token: String): Either<Failure, Map<String, String>> {
        val call = service.verifyToken(TokenDto(token))
        return request(call, { emptyMap<String, String>() }, emptyMap<String, String>())
    }

    override fun refreshToken(token: String): Either<Failure, AuthUser> {
        val call = service.refreshToken(RefreshTokenDto(token))
        return request(call, { mapper.AuthUserToDomain(it) }, mapper.emptyAuthUser())
    }

    override fun logInUser(email: String, password: String): Either<Failure, AuthUser> {
        val call = service.logInUser(AuthUserDto(email, password))
        return request(call, { mapper.AuthUserToDomain(it) }, mapper.emptyAuthUser())
    }

    override fun addProduct(title: String, description: String?, price: Int, mediaFile: File):
            Either<Failure, Product> {

        val requestBodyTitle = RequestBody.create(MediaType.parse("multipart/form"), title)
        val requestBodyDesc = RequestBody.create(MediaType.parse("multipart/form"), description ?: "")
        val requestBodyPrice = RequestBody.create(MediaType.parse("multipart/form"), price.toString())
        val requestPartImage = RequestBody.create(MediaType.parse("multipart/form"), mediaFile)

        val imagePart: MultipartBody.Part =
            MultipartBody.Part.createFormData("image", mediaFile.name, requestPartImage)

        val call = service.addProduct(requestBodyTitle, requestBodyDesc, requestBodyPrice, imagePart)
        return request(call, { mapper.productToDomain(it) }, mapper.emptyProduct())
    }

    override fun getProducts(search: String?, ofUser: Boolean): Either<Failure, List<Product>> {
        val call = when (ofUser) {
            true -> service.getUserProducts()
            else -> service.getProducts(search)
        }
        return request(call, { entity -> entity.map { mapper.productToDomain(it) } }, emptyList())
    }

    override fun deleteProduct(id: Int): Either<Failure, Unit> {
        val call = service.deleteProduct(id)
        return request(call, { Unit }, Unit)
    }

    override fun saveUserProfile(id: Int, firstName: String?, lastName: String?, postalCode: Int?, phone: String?, photo: File?):
            Either<Failure, UserProfile> {
        val call = when (photo != null) {
            true -> {
                val requestFirstName = RequestBody.create(MediaType.parse("multipart/form"), firstName ?: "")
                val requestLastName = RequestBody.create(MediaType.parse("multipart/form"), lastName ?: "")
                val requestPostalCode = RequestBody.create(MediaType.parse("multipart/form"), postalCode?.toString() ?: "")
                val requestBodyPhone = RequestBody.create(MediaType.parse("multipart/form"), phone ?: "")
                val requestPartImage = RequestBody.create(MediaType.parse("multipart/form"), photo)

                val imagePart: MultipartBody.Part =
                    MultipartBody.Part.createFormData("image", photo.name, requestPartImage)

                service.saveUserProfileWithPhoto(id, requestFirstName, requestLastName, requestPostalCode, requestBodyPhone, imagePart)
            }
            false -> service.saveUserProfile(id, UserProfileDto(firstName, lastName, postalCode, photo))
        }
        return request(call, { mapper.userProfileToDomain(it) }, mapper.emptyUserProfile())
    }

    private fun <T, R> request(call: Call<ApiResponse<T>>, transform: (T) -> R, default: T): Either<Failure, R> {
        return try {
            val response = call.execute()
            when (response.isSuccessful) {
                true -> Right(transform(response.body()?.data ?: default))
                false -> {
                    val moshi = Moshi.Builder().build()
                    val adapter = moshi.adapter(ErrorResponse::class.java)
                    val errorResponse = adapter.fromJson(response.errorBody()?.string() ?: "")
                    val message = errorResponse?.data?.error
                    Left(ServerError(message))
                }
            }
        } catch (exception: Throwable) {
            Left(ServerError())
        }
    }
}