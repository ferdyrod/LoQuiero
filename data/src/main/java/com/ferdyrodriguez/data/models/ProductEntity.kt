package com.ferdyrodriguez.data.models

data class ProductEntity(val id: Int,
                         val user_id: Int,
                         val title: String,
                         val description: String?,
                         val type: String,
                         val status: String,
                         val active: Boolean,
                         val price: Int,
                         val created: String,
                         val updated: String,
                         val transaction: TransactionEntity?)


data class TransactionEntity(val buyer: Int, val result: String, val created: String)