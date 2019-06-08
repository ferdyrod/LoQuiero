package com.ferdyrodriguez.loquiero

import android.content.Context
import com.ferdyrodriguez.domain.usecases.RegisterUserUseCase
import org.junit.runner.RunWith
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.inject
import org.mockito.Mock
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class RegistrationViewModelTest : AutoCloseKoinTest(){

    @Mock
    private val context: Context = RuntimeEnvironment.application.applicationContext

    private val useCase: RegisterUserUseCase by inject()

    val modules = module {
        factory {  }
    }
}