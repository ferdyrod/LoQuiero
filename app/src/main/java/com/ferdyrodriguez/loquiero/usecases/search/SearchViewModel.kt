package com.ferdyrodriguez.loquiero.usecases.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ferdyrodriguez.domain.models.Product
import com.ferdyrodriguez.domain.usecases.GetProductsUseCase
import com.ferdyrodriguez.loquiero.base.BaseViewModel
import com.ferdyrodriguez.loquiero.models.ProductItem

class SearchViewModel(private val usecase:GetProductsUseCase): BaseViewModel() {

    private var _products: MutableLiveData<List<ProductItem>> = MutableLiveData()
    val products: LiveData<List<ProductItem>>
        get() = _products


    fun getProducts(query: String) {
        usecase(GetProductsUseCase.Params(query)) {
            it.either(::handleFailure, ::handleProducts)
        }
    }

    fun handleProducts(products: List<Product>) {
        _products.value = products.map { ProductItem(it.id, it.title, it.description, it.price, it.image) }
    }
}