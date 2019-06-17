package com.ferdyrodriguez.domain.models

data class AuthUser(val refresh: String, val access: String, val user_id: Int?, val email: String?)