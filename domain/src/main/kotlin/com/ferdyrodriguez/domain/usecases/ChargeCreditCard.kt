package com.ferdyrodriguez.domain.usecases

import com.ferdyrodriguez.domain.MainRepository
import com.ferdyrodriguez.domain.exceptions.Failure
import com.ferdyrodriguez.domain.fp.Either
import com.ferdyrodriguez.domain.models.Product
import com.ferdyrodriguez.domain.usecases.base.UseCase

class ChargeCreditCard(private val repository: MainRepository) : UseCase<Product, ChargeCreditCard.Params>() {

    override suspend fun run(params: ChargeCreditCard.Params): Either<Failure, Product> =
        repository.chargeCreditCard(params.productId, params.cardToken)

    data class Params(val productId: Int, val cardToken: String)
}