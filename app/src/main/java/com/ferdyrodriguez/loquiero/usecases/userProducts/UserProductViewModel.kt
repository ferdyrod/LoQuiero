package com.ferdyrodriguez.loquiero.usecases.userProducts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ferdyrodriguez.domain.models.Product
import com.ferdyrodriguez.domain.usecases.DeleteProductUseCase
import com.ferdyrodriguez.domain.usecases.GetProductsUseCase
import com.ferdyrodriguez.loquiero.base.BaseViewModel
import com.ferdyrodriguez.loquiero.models.ProductItem
import com.ferdyrodriguez.loquiero.utils.Event

class UserProductViewModel(private val useCase: GetProductsUseCase,
                           private val deleteUseCase: DeleteProductUseCase) : BaseViewModel() {

    private var _products: MutableLiveData<List<ProductItem>> = MutableLiveData()
    val products: LiveData<List<ProductItem>>
        get() = _products

    private var _openMenu: MutableLiveData<Event<Pair<Int, Int>>> = MutableLiveData()
    val openMenu: LiveData<Event<Pair<Int, Int>>>
        get() = _openMenu

    private var _deletedSuccessfully: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val deletedSuccessfully: LiveData<Event<Boolean>>
        get() = _deletedSuccessfully

    fun getProducts() {
        useCase(GetProductsUseCase.Params(ofUser = true)) {
            it.either(::handleFailure, ::handleProducts)
        }
    }

    fun deleteProduct(productId: Int) {
        deleteUseCase(DeleteProductUseCase.Params(productId)) {
            it.either(::handleFailure, ::handleDeletion)
        }
    }

    fun openMenuToDelete(productId: Int, position: Int) {
        _openMenu.postValue(Event(Pair(productId, position)))
    }

    private fun handleProducts(products: List<Product>) {
        _products.value = products.map { ProductItem(it.id, it.title, it.description, it.price, it.image) }
    }

    private fun handleDeletion(unit: Unit){
        _deletedSuccessfully.value = Event(true)
    }
}