package com.ferdyrodriguez.domain.usecases

import com.ferdyrodriguez.domain.MainRepository
import com.ferdyrodriguez.domain.exceptions.Failure
import com.ferdyrodriguez.domain.fp.Either
import com.ferdyrodriguez.domain.models.Product
import com.ferdyrodriguez.domain.usecases.base.UseCase

class AddProductUseCase(private val repository: MainRepository) :
    UseCase<Product, AddProductUseCase.Params>() {

    override suspend fun run(params: Params): Either<Failure, Product> =
        repository.addProduct(params.title, params.description, params.price)

    data class Params(val title: String, val description: String?, val price: Int)
}