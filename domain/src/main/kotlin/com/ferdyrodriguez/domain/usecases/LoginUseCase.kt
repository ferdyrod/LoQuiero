package com.ferdyrodriguez.domain.usecases

import com.ferdyrodriguez.domain.MainRepository
import com.ferdyrodriguez.domain.exceptions.Failure
import com.ferdyrodriguez.domain.fp.Either
import com.ferdyrodriguez.domain.models.AuthUser
import com.ferdyrodriguez.domain.models.RegisterUser
import com.ferdyrodriguez.domain.usecases.base.UseCase

class LoginUseCase constructor(private val repository: MainRepository):
    UseCase<AuthUser, LoginUseCase.Params>() {

    override suspend fun run(params: Params): Either<Failure, AuthUser> =
        repository.logInUser(params.email, params.password)

    data class Params(val email: String, val password: String)
}