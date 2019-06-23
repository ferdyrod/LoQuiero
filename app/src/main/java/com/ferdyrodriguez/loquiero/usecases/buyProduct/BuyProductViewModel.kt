package com.ferdyrodriguez.loquiero.usecases.buyProduct

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ferdyrodriguez.domain.exceptions.Failure
import com.ferdyrodriguez.domain.models.Product
import com.ferdyrodriguez.domain.usecases.ChargeCreditCard
import com.ferdyrodriguez.loquiero.base.BaseViewModel
import com.ferdyrodriguez.loquiero.utils.Event
import com.stripe.android.Stripe
import com.stripe.android.TokenCallback
import com.stripe.android.model.Card
import com.stripe.android.model.Token

class BuyProductViewModel(private val useCase: ChargeCreditCard,
                          private val stripe: Stripe) : BaseViewModel() {

    private var _validateCard = MutableLiveData<Event<Boolean>>()
    val validateCard: LiveData<Event<Boolean>>
        get() = _validateCard

    private var _productBought = MutableLiveData<Event<Boolean>>()
    val productBought: LiveData<Event<Boolean>>
        get() = _productBought

    fun callStripe() {
        _validateCard.value = Event(true)
    }

    fun createToken(productId: Int, cardToTokenize: Card) {
        stripe.createToken(cardToTokenize, object: TokenCallback{
            override fun onSuccess(result: Token) {
                useCase(ChargeCreditCard.Params(productId, result.id)){
                    it.either(::handleFailure, ::handleProduct)
                }
            }

            override fun onError(e: Exception) {
                _failure.value = Failure.ServerError(e.message)
            }
        })
    }

    private fun handleProduct(product: Product){
        _productBought.value = Event(true)
    }

}