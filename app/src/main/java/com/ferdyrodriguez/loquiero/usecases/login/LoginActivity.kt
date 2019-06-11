package com.ferdyrodriguez.loquiero.usecases.login

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.ferdyrodriguez.domain.exceptions.Failure
import com.ferdyrodriguez.loquiero.R
import com.ferdyrodriguez.loquiero.base.BaseActivity
import com.ferdyrodriguez.loquiero.databinding.ActivityLoginBinding
import com.ferdyrodriguez.loquiero.extensions.emptyString
import com.ferdyrodriguez.loquiero.extensions.toast
import kotlinx.android.synthetic.main.toolbar.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : BaseActivity() {

    private val loginViewModel: LoginViewModel by viewModel()

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.viewModel = loginViewModel
        binding.lifecycleOwner = this

        setSupportActionBar(binding.baseToolbar.toolbar)
        supportActionBar?.let {it ->
            it.title = getString(R.string.login_title)
        }

        loginViewModel.email.observe(this, Observer(this::checkEmail))
        loginViewModel.password.observe(this, Observer(this::checkPassword))

        loginViewModel.emailError.observe(this, Observer(this::setEmailError))
        loginViewModel.passwordError.observe(this, Observer(this::setPasswordError))

        loginViewModel.isLoginComplete.observe(this, Observer(this::handleNavigation))
        loginViewModel.failure.observe(this, Observer(this::handleFailure))

    }

    private fun handleFailure(failure: Failure) {
        if(!failure.errorMessage.isNullOrEmpty())
            toast(failure.errorMessage.toString())
        else
            toast(getString(R.string.problem_try_again))
    }

    private fun handleNavigation(isLoginComplete: Boolean) {
        if(isLoginComplete){
            toast("Login is!!!")
        }
    }

    private fun setPasswordError(isError: Boolean) {
        if(isError)
            binding.passwordLayout.error = getString(R.string.short_password_error)
        else
            binding.passwordLayout.error = emptyString()
    }


    private fun setEmailError(isError: Boolean) {
        if(isError)
            binding.emailLayout.error = getString(R.string.email_format_error)
        else
            binding.emailLayout.error = emptyString()
    }

    private fun checkEmail(email: String){
        loginViewModel.validateEmail(email)
    }

    private fun checkPassword(password: String){
        loginViewModel.validatePassword(password)
    }

}
