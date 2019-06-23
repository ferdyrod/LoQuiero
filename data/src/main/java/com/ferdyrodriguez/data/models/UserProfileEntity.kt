package com.ferdyrodriguez.data.models

data class UserProfileEntity(val user_id: Int,
                             val email: String,
                             val first_name: String?,
                             val last_name: String?,
                             val postal_code: Int?,
                             val phone: String?,
                             val ip: String?,
                             val image: String?,
                             val stripe_id: String?)