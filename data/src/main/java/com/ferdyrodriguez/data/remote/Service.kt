package com.ferdyrodriguez.data.remote

import com.ferdyrodriguez.data.models.ApiResponse
import com.ferdyrodriguez.data.models.AuthUserEntity
import com.ferdyrodriguez.data.models.ProductEntity
import com.ferdyrodriguez.data.models.RegisterUserEntity
import com.ferdyrodriguez.data.models.dto.AuthUserDto
import com.ferdyrodriguez.data.models.dto.RefreshTokenDto
import com.ferdyrodriguez.data.models.dto.RegisterUserDto
import com.ferdyrodriguez.data.models.dto.TokenDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.*


class ApiService(retrofit: Retrofit) : Service {
    private val apiService by lazy { retrofit.create(Service::class.java) }

    override fun logInUser(user: AuthUserDto) = apiService.logInUser(user)
    override fun refreshToken(token: RefreshTokenDto): Call<ApiResponse<AuthUserEntity>> = apiService.refreshToken(token)
    override fun verifyToken(token: TokenDto) = apiService.verifyToken(token)
    override fun registerUser(user: RegisterUserDto) = apiService.registerUser(user)
    override fun addProduct(product: RequestBody,
                            requestBodyDesc: RequestBody,
                            requestBodyprice: RequestBody,
                            imagePart: MultipartBody.Part) =
        apiService.addProduct(product, requestBodyDesc, requestBodyprice, imagePart)

    override fun getProducts(search: String?): Call<ApiResponse<List<ProductEntity>>> = apiService.getProducts(search)
    override fun getUserProducts(): Call<ApiResponse<List<ProductEntity>>> = apiService.getUserProducts()
    override fun deleteProduct(id: Int): Call<ApiResponse<Any>> = apiService.deleteProduct(id)
}


interface Service {

    companion object {

        private const val AUTH = "auth"
        private const val USER = "user"
        private const val PUBLICATIONS = "publications"

        private const val REGISTER_USER: String = "$USER/"
        private const val AUTH_USER: String = "$AUTH/"
        private const val RERESH_TOKEN: String = "$AUTH/refresh/"
        private const val VERIFY_TOKEN: String = "$AUTH/verify/"
        private const val PRODUCTS: String = "$PUBLICATIONS"
        private const val USER_PRODUCTS: String = "$PUBLICATIONS/$USER/"
        private const val USER_PRODUCTS_ID: String = "$PUBLICATIONS/$USER/{id}/"

    }

    @POST(REGISTER_USER)
    fun registerUser(@Body user: RegisterUserDto): Call<ApiResponse<RegisterUserEntity>>

    @POST(AUTH_USER)
    fun logInUser(@Body user: AuthUserDto): Call<ApiResponse<AuthUserEntity>>

    @POST(VERIFY_TOKEN)
    fun verifyToken(@Body token: TokenDto): Call<ApiResponse<Any>>

    @POST(RERESH_TOKEN)
    fun refreshToken(@Body token: RefreshTokenDto): Call<ApiResponse<AuthUserEntity>>

    @Multipart
    @POST(USER_PRODUCTS)
    fun addProduct(
        @Part("title") title: RequestBody,
        @Part("description") description: RequestBody,
        @Part("price") price: RequestBody,
        @Part imagePart: MultipartBody.Part): Call<ApiResponse<ProductEntity>>

    @GET(PRODUCTS)
    fun getProducts(@Query("search") search: String?): Call<ApiResponse<List<ProductEntity>>>

    @GET(USER_PRODUCTS)
    fun getUserProducts(): Call<ApiResponse<List<ProductEntity>>>

    @DELETE(USER_PRODUCTS_ID)
    fun deleteProduct(@Path("id") id: Int): Call<ApiResponse<Any>>
}