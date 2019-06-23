package com.ferdyrodriguez.loquiero.usecases.productDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ferdyrodriguez.data.local.PreferenceHelper
import com.ferdyrodriguez.loquiero.base.BaseViewModel
import com.ferdyrodriguez.loquiero.models.ProductItem
import com.ferdyrodriguez.loquiero.utils.Constants
import com.ferdyrodriguez.loquiero.utils.Event

class ProductDetailViewModel(private val product: ProductItem,
                             prefs: PreferenceHelper) : BaseViewModel() {

    val userId: Int = prefs.getPreference(Constants.USER_ID, -1) ?: -1

    private var _navigationToBuy = MutableLiveData<Event<Int>>()
    val navigateToBuy: LiveData<Event<Int>>
        get() = _navigationToBuy


    fun goToBuy(){
        _navigationToBuy.value = Event(product.id)
    }
}