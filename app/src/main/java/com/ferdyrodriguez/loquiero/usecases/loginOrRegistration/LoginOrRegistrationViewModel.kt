package com.ferdyrodriguez.loquiero.usecases.loginOrRegistration

import com.ferdyrodriguez.loquiero.base.BaseViewModel
import com.ferdyrodriguez.loquiero.navigation.Navigator

class LoginOrRegistrationViewModel(private val navigator: Navigator) : BaseViewModel() {


    fun  goToLogin() {
        navigator.toLogin()
    }

    fun goToRegistration() {
        navigator.toRegistration()
    }

}