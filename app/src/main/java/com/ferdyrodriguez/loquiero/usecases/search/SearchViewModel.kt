package com.ferdyrodriguez.loquiero.usecases.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ferdyrodriguez.domain.models.Product
import com.ferdyrodriguez.domain.usecases.GetProductsUseCase
import com.ferdyrodriguez.loquiero.base.BaseViewModel
import com.ferdyrodriguez.loquiero.models.ProductItem
import com.ferdyrodriguez.loquiero.utils.Event
import com.ferdyrodriguez.loquiero.utils.State

class SearchViewModel(private val usecase:GetProductsUseCase): BaseViewModel() {

    private var _navigationToDetail = MutableLiveData<Event<ProductItem>>()
    val navigateToDetail: LiveData<Event<ProductItem>>
        get() = _navigationToDetail

    private var _products: MutableLiveData<List<ProductItem>> = MutableLiveData()
    val products: LiveData<List<ProductItem>>
        get() = _products


    fun getProducts(query: String) {
        _state.value = State.LOADING
        usecase(GetProductsUseCase.Params(query)) {
            it.either(::handleFailure, ::handleProducts)
        }
    }

    fun handleProducts(products: List<Product>) {
        _state.value = State.FINISHED
        _products.value = products.map { ProductItem(it.id, it.user_id, it.title, it.description, it.price, it.image) }
    }

    fun goToProduct(product: ProductItem) {
        _navigationToDetail.value = Event(product)
    }
}