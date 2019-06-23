package com.ferdyrodriguez.loquiero.usecases.splash

import android.os.Bundle
import com.ferdyrodriguez.loquiero.R
import com.ferdyrodriguez.loquiero.base.BaseActivity
import kotlinx.coroutines.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity : BaseActivity() {

    private val splashViewModel : SplashViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val job = Job()
        val scope = CoroutineScope(Dispatchers.Main + job)

        scope.launch {
            delay(1000)
            splashViewModel.verifyLogin()
        }

    }
}
