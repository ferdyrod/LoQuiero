package com.ferdyrodriguez.loquiero.extensions

import android.content.Context
import android.widget.Toast

fun Context.toast(message: String){
    return Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}