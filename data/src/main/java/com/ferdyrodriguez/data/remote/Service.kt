package com.ferdyrodriguez.data.remote

import com.ferdyrodriguez.data.models.ApiResponse
import com.ferdyrodriguez.data.models.RegisterUserEntity
import com.ferdyrodriguez.data.models.dto.RegisterUserDto
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.POST


class ApiService(retrofit: Retrofit) : Service {
    private val apiService by lazy { retrofit.create(Service::class.java) }

    override fun registerUser(user: RegisterUserDto) = apiService.registerUser(user)

}



interface Service {

    companion object {
        private const val REGISTER_USER: String = "user/"
    }

    @POST(REGISTER_USER)
    fun registerUser(@Body user: RegisterUserDto) : Call<ApiResponse<RegisterUserEntity>>
}