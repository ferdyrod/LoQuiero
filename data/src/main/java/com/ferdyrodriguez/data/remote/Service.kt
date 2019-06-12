package com.ferdyrodriguez.data.remote

import com.ferdyrodriguez.data.models.ApiResponse
import com.ferdyrodriguez.data.models.AuthUserEntity
import com.ferdyrodriguez.data.models.RegisterUserEntity
import com.ferdyrodriguez.data.models.dto.AuthUserDto
import com.ferdyrodriguez.data.models.dto.RegisterUserDto
import com.ferdyrodriguez.data.models.dto.TokenDto
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.POST


class ApiService(retrofit: Retrofit) : Service {
    private val apiService by lazy { retrofit.create(Service::class.java) }

    override fun logInUser(user: AuthUserDto) = apiService.logInUser(user)
    override fun refreshToken(token: TokenDto): Call<ApiResponse<AuthUserEntity>> = apiService.refreshToken(token)
    override fun verifyToken(token: TokenDto) = apiService.verifyToken(token)
    override fun registerUser(user: RegisterUserDto) = apiService.registerUser(user)
}


interface Service {

    companion object {
        private const val REGISTER_USER: String = "user/"
        private const val AUTH_USER: String = "auth/"
        private const val RERESH_TOKEN: String = "${AUTH_USER}refresh/"
        private const val VERIFY_TOKEN: String = "${AUTH_USER}verify/"

    }

    @POST(REGISTER_USER)
    fun registerUser(@Body user: RegisterUserDto): Call<ApiResponse<RegisterUserEntity>>

    @POST(AUTH_USER)
    fun logInUser(@Body user: AuthUserDto): Call<ApiResponse<AuthUserEntity>>

    @POST(VERIFY_TOKEN)
    fun verifyToken(@Body token: TokenDto): Call<ApiResponse<Any>>

    @POST(RERESH_TOKEN)
    fun refreshToken(@Body token: TokenDto): Call<ApiResponse<AuthUserEntity>>
}