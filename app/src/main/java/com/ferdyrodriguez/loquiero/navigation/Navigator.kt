package com.ferdyrodriguez.loquiero.navigation

import android.content.Context
import android.content.Intent
import com.ferdyrodriguez.loquiero.usecases.login.LoginActivity
import com.ferdyrodriguez.loquiero.usecases.loginOrRegistration.LoginOrRegistrationActivity
import com.ferdyrodriguez.loquiero.usecases.main.MainActivity
import com.ferdyrodriguez.loquiero.usecases.registration.RegistrationActivity
class Navigator constructor(private val context: Context) {

    fun toLoginOrRegistration() {
        val intent = Intent(context, LoginOrRegistrationActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        context.startActivity(intent)
    }

    fun toRegistration() {
        val intent = Intent(context, RegistrationActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    fun toLogin() {
        val intent = Intent(context, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    fun toMain() {
        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    fun toProfileRegistration() {

    }
}