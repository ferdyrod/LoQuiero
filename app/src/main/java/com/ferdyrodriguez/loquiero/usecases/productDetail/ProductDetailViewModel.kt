package com.ferdyrodriguez.loquiero.usecases.productDetail

import com.ferdyrodriguez.data.local.PreferenceHelper
import com.ferdyrodriguez.loquiero.base.BaseViewModel
import com.ferdyrodriguez.loquiero.models.ProductItem
import com.ferdyrodriguez.loquiero.utils.Constants

class ProductDetailViewModel(private val product: ProductItem,
                             prefs: PreferenceHelper) : BaseViewModel() {

    val userId: Int = prefs.getPreference(Constants.USER_ID, -1) ?: -1



}