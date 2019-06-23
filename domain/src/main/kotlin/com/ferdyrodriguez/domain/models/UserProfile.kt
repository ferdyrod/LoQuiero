package com.ferdyrodriguez.domain.models

data class UserProfile(val user_id: Int,
                       val email: String,
                       val firstName: String?,
                       val lastName: String?,
                       val postalCode: Int?,
                       val phone: String?,
                       val ip: String?,
                       val photo: String?,
                       val stripe_id: String?)
