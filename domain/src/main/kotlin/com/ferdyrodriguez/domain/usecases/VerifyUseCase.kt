package com.ferdyrodriguez.domain.usecases

import com.ferdyrodriguez.domain.MainRepository
import com.ferdyrodriguez.domain.exceptions.Failure
import com.ferdyrodriguez.domain.fp.Either
import com.ferdyrodriguez.domain.usecases.base.UseCase

class VerifyUseCase(private val repository: MainRepository):
    UseCase<Map<String, String>, UseCase.None>() {


    override suspend fun run(params: None): Either<Failure, Map<String, String>> =
            repository.verifyToken()


}