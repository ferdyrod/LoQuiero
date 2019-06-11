package com.ferdyrodriguez.data

import com.ferdyrodriguez.data.models.ApiResponse
import com.ferdyrodriguez.data.models.RegisterUserEntity
import com.ferdyrodriguez.domain.exceptions.Failure
import com.ferdyrodriguez.domain.fp.Either
import com.ferdyrodriguez.domain.models.AuthUser
import com.ferdyrodriguez.domain.models.RegisterUser
import org.mockito.Mock
import retrofit2.Call
import retrofit2.Response

val emptyRegisterUser = RegisterUser(0, "")
val emptyRegisterEither: Either<Failure, RegisterUser> = Either.Right(emptyRegisterUser)
val emptyAuthUser = AuthUser("", "")
val emptyAuthEither: Either<Failure, AuthUser> = Either.Right(emptyAuthUser)


@Mock lateinit var registerResponse: Response<ApiResponse<RegisterUserEntity>>
@Mock lateinit var registarCall: Call<ApiResponse<RegisterUserEntity>>