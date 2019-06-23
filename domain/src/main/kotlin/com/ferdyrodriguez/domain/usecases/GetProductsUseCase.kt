package com.ferdyrodriguez.domain.usecases

import com.ferdyrodriguez.domain.MainRepository
import com.ferdyrodriguez.domain.exceptions.Failure
import com.ferdyrodriguez.domain.fp.Either
import com.ferdyrodriguez.domain.models.Product
import com.ferdyrodriguez.domain.usecases.base.UseCase

class GetProductsUseCase(private val repository: MainRepository): UseCase<List<Product>, GetProductsUseCase.Params>() {

    override suspend fun run(params: Params): Either<Failure, List<Product>> = repository.getProducts(params.search, params.ofUser)

    data class Params(var search: String? = null, val ofUser: Boolean = false)

}