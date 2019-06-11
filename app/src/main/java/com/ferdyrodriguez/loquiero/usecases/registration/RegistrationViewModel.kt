package com.ferdyrodriguez.loquiero.usecases.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ferdyrodriguez.domain.models.RegisterUser
import com.ferdyrodriguez.domain.usecases.RegisterUserUseCase
import com.ferdyrodriguez.loquiero.base.BaseViewModel
import com.ferdyrodriguez.loquiero.extensions.isEmail
import com.ferdyrodriguez.loquiero.utils.Constants
import com.ferdyrodriguez.loquiero.utils.PreferenceHelper


class RegistrationViewModel(
    private val useCase: RegisterUserUseCase,
    private val prefs: PreferenceHelper
) : BaseViewModel() {

    var email: MutableLiveData<String> = MutableLiveData()
    private var _emailError: MutableLiveData<Boolean> = MutableLiveData()
    val emailError: LiveData<Boolean>
        get() = _emailError
    var password: MutableLiveData<String> = MutableLiveData()
    private var _passwordError: MutableLiveData<Boolean> = MutableLiveData()
    val passwordError: LiveData<Boolean>
        get() = _passwordError
    var password2: MutableLiveData<String> = MutableLiveData()
    private var _password2Error: MutableLiveData<Boolean> = MutableLiveData()
    val password2Error: LiveData<Boolean>
        get() = _password2Error
    private val _formIsValid: MutableLiveData<Boolean> = MutableLiveData()
    val formIsValid: LiveData<Boolean>
        get() = isFormValid()

    private val _isUserRegistrationComplete: MutableLiveData<Boolean> = MutableLiveData()
    val isUserRegistrationComplete: LiveData<Boolean>
        get() = _isUserRegistrationComplete

    private var validEmail: Boolean = false
    private var validPassword: Boolean = false
    private var validPassword2: Boolean = false

    init {
        _formIsValid.value = false
        _isUserRegistrationComplete.value = false
    }

    fun registerUser() {
        useCase(RegisterUserUseCase.Params(email.value!!, password.value!!)) {
            it.either(::handleFailure, ::handleRegistration)
        }
    }

    private fun handleRegistration(user: RegisterUser) {
        prefs.setPreference(Constants.USER_ID, user.id)
        prefs.setPreference(Constants.USER_EMAIL, user.email)
        _isUserRegistrationComplete.value = true
    }

    private fun isFormValid(): MutableLiveData<Boolean> {
        _formIsValid.value = validEmail && validPassword && validPassword2
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
        when {
            password.length > 5 -> {
                validPassword = true
                _passwordError.value = false
                when {
                    password2.value != password -> {
                        validPassword2 = false
                        _password2Error.value = true
                    }
                    else -> {
                        validPassword2 = true
                        _password2Error.value = false
                    }
                }
            }
            else -> {
                validPassword = false
                _passwordError.value = true
            }
        }
        isFormValid()
    }

    fun validatePassword2(password2: String) {
        if (password2.equals(password.value)) {
            validPassword2 = true
            _password2Error.value = false
        } else {
            validPassword2 = false
            _password2Error.value = true
        }
        isFormValid()
    }
}