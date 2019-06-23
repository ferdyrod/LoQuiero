package com.ferdyrodriguez.loquiero.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductItem(
    val id: Int,
    val seller_id: Int,
    val title: String?,
    val description: String?,
    val price: Int,
    val image: String?
) : Parcelable