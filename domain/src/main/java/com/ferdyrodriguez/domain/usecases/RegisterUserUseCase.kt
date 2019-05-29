package com.ferdyrodriguez.domain.usecases

import com.ferdyrodriguez.domain.MainRepository
import com.ferdyrodriguez.domain.exceptions.Failure
import com.ferdyrodriguez.domain.fp.Either
import com.ferdyrodriguez.domain.models.RegisterUser
import com.ferdyrodriguez.domain.usecases.base.UseCase
import org.koin.core.KoinComponent
import org.koin.core.inject

class RegisterUserUseCase :
    UseCase<RegisterUser, RegisterUserUseCase.Params>(), KoinComponent {

    private val repository: MainRepository by inject()

    override suspend fun run(params: Params): Either<Failure, RegisterUser> =
        repository.registerUser(params.email, params.password)

    data class Params(val email: String, val password: String)
}