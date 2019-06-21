package com.ferdyrodriguez.loquiero.usecases.main


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ferdyrodriguez.data.local.PreferenceConstants
import com.ferdyrodriguez.data.local.PreferenceHelper
import com.ferdyrodriguez.domain.models.Product
import com.ferdyrodriguez.domain.usecases.GetProductsUseCase
import com.ferdyrodriguez.loquiero.base.BaseViewModel
import com.ferdyrodriguez.loquiero.models.ProductItem
import com.ferdyrodriguez.loquiero.utils.Event

class MainViewModel(private val useCase: GetProductsUseCase,
                    private val prefs: PreferenceHelper) : BaseViewModel() {

    private var _navigationToAdd = MutableLiveData<Event<Boolean>>()
    val navigateToAdd: LiveData<Event<Boolean>>
        get() = _navigationToAdd

    private var _products: MutableLiveData<List<ProductItem>> = MutableLiveData()
    val products: LiveData<List<ProductItem>>
        get() = _products

    var photo: MutableLiveData<String> = MutableLiveData()

    fun goToAddProduct() {
        _navigationToAdd.value = Event(true)
    }

    fun getProducts() {
        useCase(GetProductsUseCase.Params()) {
            it.either(::handleFailure, ::handleProducts)
        }
    }

    fun handleProducts(products: List<Product>) {
        _products.value = products.map { ProductItem(it.id, it.title, it.description, it.price, it.image) }
    }

    fun getPhoto(){
        photo.value = prefs.getPreference(PreferenceConstants.USER_PHOTO, "")
    }
}