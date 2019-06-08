package com.ferdyrodriguez.loquiero.extensions


fun String.isEmail(): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun emptyString() : String{
    return ""
}