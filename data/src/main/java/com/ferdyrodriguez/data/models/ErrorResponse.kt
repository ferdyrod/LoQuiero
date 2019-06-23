package com.ferdyrodriguez.data.models

import com.squareup.moshi.Json

data class ErrorResponse(@Json(name = "data") val data: ErrorMessage)

data class ErrorMessage(val error: String)


