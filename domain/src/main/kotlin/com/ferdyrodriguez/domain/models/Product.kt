package com.ferdyrodriguez.domain.models

data class Product(val id: Int,
                   val user_id: Int,
                   val title: String,
                   val description: String?,
                   val type: String,
                   val status: String,
                   val active: Boolean,
                   val price: Int,
                   val created: String,
                   val updated: String,
                   val transaction: Transaction?)