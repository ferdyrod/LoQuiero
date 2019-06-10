package com.ferdyrodriguez.domain.usecases

import com.ferdyrodriguez.domain.MainRepository
import com.ferdyrodriguez.domain.exceptions.Failure
import com.ferdyrodriguez.domain.fp.Either
import com.ferdyrodriguez.domain.models.RegisterUser
import com.ferdyrodriguez.domain.usecases.base.UseCase

class RegisterUserUseCase constructor(private val repository: MainRepository):
    UseCase<RegisterUser, RegisterUserUseCase.Params>() {

    override suspend fun run(params: Params): Either<Failure, RegisterUser> =
        repository.registerUser(params.email, params.password)

    data class Params(val email: String, val password: String)
}