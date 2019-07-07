package com.ferdyrodriguez.data.remote

import com.ferdyrodriguez.data.models.*
import com.ferdyrodriguez.data.models.dto.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.*


class ApiService(retrofit: Retrofit) : Service {
    private val apiService by lazy { retrofit.create(Service::class.java) }

    override fun logInUser(user: AuthUserDto) = apiService.logInUser(user)
    override fun refreshToken(token: RefreshTokenDto): Call<ApiResponse<AuthUserEntity>> =
        apiService.refreshToken(token)

    override fun verifyToken(token: TokenDto) = apiService.verifyToken(token)
    override fun registerUser(user: RegisterUserDto) = apiService.registerUser(user)
    override fun addProduct(
        title: RequestBody,
        description: RequestBody,
        price: RequestBody,
        imagePart: MultipartBody.Part
    ) =
        apiService.addProduct(title, description, price, imagePart)

    override fun getProducts(search: String?): Call<ApiResponse<List<ProductEntity>>> = apiService.getProducts(search)
    override fun getUserProducts(): Call<ApiResponse<List<ProductEntity>>> = apiService.getUserProducts()
    override fun deleteProduct(id: Int): Call<ApiResponse<Any>> = apiService.deleteProduct(id)
    override fun saveUserProfileWithPhoto(
        id: Int,
        firstName: RequestBody,
        lastName: RequestBody,
        postalCode: RequestBody,
        phone: RequestBody,
        imagePart: MultipartBody.Part
    ): Call<ApiResponse<UserProfileEntity>> =
        apiService.saveUserProfileWithPhoto(id, firstName, lastName, postalCode, phone, imagePart)

    override fun saveUserProfile(id: Int, userProfile: UserProfileDto): Call<ApiResponse<UserProfileEntity>> =
        apiService.saveUserProfile(id, userProfile)

    override fun chargeCreditCard(productId: Int, charge: ChargeDto): Call<ApiResponse<ProductEntity>> =
        apiService.chargeCreditCard(productId, charge)

    override fun getUserProfile(user_id: Int?): Call<ApiResponse<UserProfileEntity>> =
        apiService.getUserProfile(user_id)
}


interface Service {

    companion object {

        private const val AUTH = "auth"
        private const val USER = "user"
        private const val PUBLICATIONS = "products"

        private const val REGISTER_USER: String = "$USER/"
        private const val AUTH_USER: String = "$AUTH/"
        private const val RERESH_TOKEN: String = "$AUTH/refresh/"
        private const val VERIFY_TOKEN: String = "$AUTH/verify/"
        private const val PRODUCTS: String = "$PUBLICATIONS"
        private const val PRODUCT_BUY: String = "$PUBLICATIONS/{id}/buy/"
        private const val USER_PRODUCTS: String = "$PUBLICATIONS/$USER/"
        private const val USER_PRODUCTS_ID: String = "$PUBLICATIONS/$USER/{id}/"
        private const val USER_PROFILE_ID: String = "$USER/{id}/"

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
        @Part imagePart: MultipartBody.Part
    ): Call<ApiResponse<ProductEntity>>

    @GET(PRODUCTS)
    fun getProducts(@Query("search") search: String?): Call<ApiResponse<List<ProductEntity>>>

    @GET(USER_PRODUCTS)
    fun getUserProducts(): Call<ApiResponse<List<ProductEntity>>>

    @DELETE(USER_PRODUCTS_ID)
    fun deleteProduct(@Path("id") id: Int): Call<ApiResponse<Any>>

    @Multipart
    @PATCH(USER_PROFILE_ID)
    fun saveUserProfileWithPhoto(
        @Path("id") id: Int,
        @Part("first_name") firstName: RequestBody,
        @Part("last_name") lastName: RequestBody,
        @Part("postal_code") postalCode: RequestBody,
        @Part("phone") phone: RequestBody,
        @Part imagePart: MultipartBody.Part
    ): Call<ApiResponse<UserProfileEntity>>

    @PATCH(USER_PROFILE_ID)
    fun saveUserProfile(
        @Path("id") id: Int,
        @Body userProfile: UserProfileDto
    ): Call<ApiResponse<UserProfileEntity>>

    @POST(PRODUCT_BUY)
    fun chargeCreditCard(
        @Path("id") productId: Int,
        @Body charge: ChargeDto
    ): Call<ApiResponse<ProductEntity>>

    @GET(USER_PROFILE_ID)
    fun getUserProfile(@Path("id") user_id: Int?): Call<ApiResponse<UserProfileEntity>>
}