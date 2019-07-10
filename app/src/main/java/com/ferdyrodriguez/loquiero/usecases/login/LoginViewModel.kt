package com.ferdyrodriguez.loquiero.usecases.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ferdyrodriguez.domain.models.AuthUser
import com.ferdyrodriguez.domain.usecases.LoginUseCase
import com.ferdyrodriguez.loquiero.base.BaseViewModel
import com.ferdyrodriguez.loquiero.extensions.isEmail
import com.ferdyrodriguez.loquiero.utils.State


class LoginViewModel(
    private val useCase: LoginUseCase) : BaseViewModel() {

    var email: MutableLiveData<String> = MutableLiveData()
    private var _emailError: MutableLiveData<Boolean> = MutableLiveData()
    val emailError: LiveData<Boolean>
        get() = _emailError
    var password: MutableLiveData<String> = MutableLiveData()
    private var _passwordError: MutableLiveData<Boolean> = MutableLiveData()
    val passwordError: LiveData<Boolean>
        get() = _passwordError
    private val _formIsValid: MutableLiveData<Boolean> = MutableLiveData()
    val formIsValid: LiveData<Boolean>
        get() = isFormValid()
    private val _isLoginComplete: MutableLiveData<Boolean> = MutableLiveData()
    val isLoginComplete: LiveData<Boolean>
        get() = _isLoginComplete

    private var validEmail: Boolean = false
    private var validPassword: Boolean = false

    init {
        _formIsValid.value = false
        _isLoginComplete.value = false
    }

    fun loginUser() {
        _state.value = State.LOADING
        useCase(LoginUseCase.Params(email.value!!, password.value!!)) {
            it.either(::handleFailure, ::handleLogin)
        }
    }

    private fun handleLogin(user: AuthUser) {
        _state.value = State.FINISHED
        _isLoginComplete.value = true
    }

    private fun isFormValid(): MutableLiveData<Boolean> {
        _formIsValid.value = validEmail && validPassword
        return _formIsValid
    }


    fun validateEmail(email: String) {
        if (email.isEmail()) {
            validEmail = true
            _emailError.value = false
        } else {
            validEmail = false
            _emailError.value = true
        }
        isFormValid()
    }

    fun validatePassword(password: String) {
        if (password.length > 5) {
            validPassword = true
            _passwordError.value = false
        } else {
            validPassword = false
            _passwordError.value = true
        }
        isFormValid()
    }

}