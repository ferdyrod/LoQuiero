package com.ferdyrodriguez.loquiero.usecases.splash

import com.ferdyrodriguez.domain.usecases.VerifyUseCase
import com.ferdyrodriguez.domain.usecases.base.UseCase
import com.ferdyrodriguez.loquiero.base.BaseViewModel
import com.ferdyrodriguez.loquiero.navigation.Navigator

class SplashViewModel(private val verifyUseCase: VerifyUseCase,
                      private val navigator: Navigator): BaseViewModel() {

    fun verifyLogin() {
        verifyUseCase(UseCase.None()) {
            it.either({ navigator.toLoginOrRegistration() }, {  })

        }
    }
}