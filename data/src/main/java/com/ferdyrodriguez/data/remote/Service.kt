package com.ferdyrodriguez.data.remote

import com.ferdyrodriguez.data.models.ApiResponse
import com.ferdyrodriguez.data.models.AuthUserEntity
import com.ferdyrodriguez.data.models.RegisterUserEntity
import com.ferdyrodriguez.data.models.dto.AuthUserDto
import com.ferdyrodriguez.data.models.dto.RegisterUserDto
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.POST


class ApiService(retrofit: Retrofit) : Service {
    private val apiService by lazy { retrofit.create(Service::class.java) }

    override fun registerUser(user: RegisterUserDto) = apiService.registerUser(user)
    override fun logInUser(user: AuthUserDto) = apiService.logInUser(user)
}



interface Service {

    companion object {
        private const val REGISTER_USER: String = "user/"
        private const val AUTH_USER: String = "auth/"
    }

    @POST(REGISTER_USER)
    fun registerUser(@Body user: RegisterUserDto) : Call<ApiResponse<RegisterUserEntity>>

    @POST(AUTH_USER)
    fun logInUser(@Body user: AuthUserDto): Call<ApiResponse<AuthUserEntity>>
}