package com.ferdyrodriguez.loquiero.usecases.registration

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.ferdyrodriguez.domain.exceptions.Failure
import com.ferdyrodriguez.loquiero.R
import com.ferdyrodriguez.loquiero.base.BaseActivity
import com.ferdyrodriguez.loquiero.databinding.ActivityRegistrationBinding
import com.ferdyrodriguez.loquiero.extensions.emptyString
import com.ferdyrodriguez.loquiero.extensions.toast
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegistrationActivity : BaseActivity() {

    private val viewModel: RegistrationViewModel by viewModel()

    private lateinit var binding: ActivityRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_registration)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setSupportActionBar(binding.baseToolbar.toolbar)
        supportActionBar?.let {it ->
            it.title = "Registrar Usuario"
        }

        viewModel.email.observe(this, Observer(this::checkEmail))
        viewModel.password.observe(this, Observer(this::checkPassword))
        viewModel.password2.observe(this, Observer(this::checkPassword2))

        viewModel.emailError.observe(this, Observer(this::setEmailError))
        viewModel.passwordError.observe(this, Observer(this::setPasswordError))
        viewModel.password2Error.observe(this, Observer(this::setPassword2Error))

        viewModel.state.observe(this, Observer(this::renderDataState))
        viewModel.isUserRegistrationComplete.observe(this, Observer(this::handleNavigation))
        viewModel.failure.observe(this, Observer(this::handleFailure))

    }

    private fun handleFailure(failure: Failure) {
        if(!failure.errorMessage.isNullOrEmpty())
            toast(failure.errorMessage.toString())
        else
            toast(getString(R.string.problem_try_again))
    }

    private fun handleNavigation(isUserRegisterComplete: Boolean) {
        if(isUserRegisterComplete){
            navigator.toMain(true)
        }
    }

    private fun setPasswordError(isError: Boolean) {
        if(isError)
            binding.passwordLayout.error = getString(R.string.short_password_error)
        else
            binding.passwordLayout.error = emptyString()
    }

    private fun setPassword2Error(isError: Boolean) {
        if(isError)
            binding.confirmPasswordLayout.error = getString(R.string.password_dont_match_error)
        else
            binding.confirmPasswordLayout.error = emptyString()
    }

    private fun setEmailError(isError: Boolean) {
        if(isError)
            binding.emailLayout.error = getString(R.string.email_format_error)
        else
            binding.emailLayout.error = emptyString()
    }

    private fun checkEmail(email: String){
        viewModel.validateEmail(email)
    }

    private fun checkPassword(password: String){
        viewModel.validatePassword(password)
    }

    private fun checkPassword2(password2: String){
        viewModel.validatePassword2(password2)
    }
}
