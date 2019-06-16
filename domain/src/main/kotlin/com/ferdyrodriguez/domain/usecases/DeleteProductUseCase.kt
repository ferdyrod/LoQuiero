package com.ferdyrodriguez.domain.usecases

import com.ferdyrodriguez.domain.MainRepository
import com.ferdyrodriguez.domain.exceptions.Failure
import com.ferdyrodriguez.domain.fp.Either
import com.ferdyrodriguez.domain.usecases.base.UseCase

class DeleteProductUseCase(private val repository: MainRepository): UseCase<Unit, DeleteProductUseCase.Params>() {

    override suspend fun run(params: Params): Either<Failure, Unit> = repository.deleteProduct(params.id)

    data class Params(val id: Int)
}