package com.ferdyrodriguez.data.remote

import com.ferdyrodriguez.data.dataSource.RemoteDataSource
import com.ferdyrodriguez.data.models.ApiResponse
import com.ferdyrodriguez.data.models.ModelMapper
import com.ferdyrodriguez.data.models.dto.RegisterUserDto
import com.ferdyrodriguez.domain.exceptions.Failure
import com.ferdyrodriguez.domain.exceptions.Failure.ServerError
import com.ferdyrodriguez.domain.fp.Either
import com.ferdyrodriguez.domain.fp.Either.Left
import com.ferdyrodriguez.domain.fp.Either.Right
import com.ferdyrodriguez.domain.models.RegisterUser
import retrofit2.Call

class RemoteDataSourceImpl constructor(private val service: ApiService,
                                       private val mapper: ModelMapper) : RemoteDataSource {


    override fun registerUser(email: String, password: String): Either<Failure, RegisterUser> {
        val call = service.registerUser(RegisterUserDto(email, password))
        return request(call, { mapper.RegisterUserToDomain(it) }, mapper.emptyRegisterUsed())
    }


    private fun <T, R> request(call: Call<ApiResponse<T>>, transform: (T) -> R, default: T): Either<Failure, R> {
        return try {
            val response = call.execute()
            when (response.isSuccessful) {
                true -> Right(transform(response.body()?.data ?: default))
                false -> Left(ServerError())
            }
        } catch (exception: Throwable) {
            Left(ServerError())
        }
    }
}