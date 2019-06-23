package com.ferdyrodriguez.loquiero.usecases.loginOrRegistration

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.ferdyrodriguez.loquiero.R
import com.ferdyrodriguez.loquiero.base.BaseActivity
import com.ferdyrodriguez.loquiero.databinding.ActivityLoginOrRegistrationBinding
import org.koin.android.ext.android.inject

class LoginOrRegistrationActivity : BaseActivity() {

    private lateinit var binding: ActivityLoginOrRegistrationBinding
    private val viewModel : LoginOrRegistrationViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login_or_registration)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }
}
