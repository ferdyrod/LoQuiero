package com.ferdyrodriguez.domain.usecases

import com.ferdyrodriguez.domain.MainRepository
import com.ferdyrodriguez.domain.exceptions.Failure
import com.ferdyrodriguez.domain.fp.Either
import com.ferdyrodriguez.domain.models.Product
import com.ferdyrodriguez.domain.usecases.base.UseCase

class GetProductsUseCase(val repository: MainRepository): UseCase<List<Product>, UseCase.None>() {

    override suspend fun run(params: None): Either<Failure, List<Product>> = repository.getProducts()

}