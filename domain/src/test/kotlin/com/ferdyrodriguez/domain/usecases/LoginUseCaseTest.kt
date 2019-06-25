package com.ferdyrodriguez.domain.usecases

import com.ferdyrodriguez.domain.MainRepository
import com.ferdyrodriguez.domain.exceptions.Failure
import com.ferdyrodriguez.domain.fp.Either
import com.ferdyrodriguez.domain.models.AuthUser
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.willReturn
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBe
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.inject
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class LoginUseCaseTest : AutoCloseKoinTest() {

    val modules = module(override = true) {
        single { LoginUseCase(repository) }
    }

    @Mock private lateinit var repository: MainRepository
    private val useCase: LoginUseCase by inject()

    @Before
    fun setUp() {
        startKoin { listOf(modules)}
    }


    @Test
    fun `should login user successfully`() {
        loadKoinModules(modules)
        given { repository.logInUser("email", "password") } willReturn { Either.Right(AuthUser("refreshToken", "authToken", 0, "")) }

        runBlocking { useCase.run(LoginUseCase.Params("email", "password")) }

        verify(repository).logInUser("email", "password")
        verifyNoMoreInteractions(repository)
        repository.logInUser("email", "password").isRight shouldBe true
    }

    @Test
    fun `should return error when there is a problem`(){
        loadKoinModules(modules)

        given { repository.logInUser("email", "password") } willReturn { Either.Left(Failure.ServerError()) }

        runBlocking { useCase.run(LoginUseCase.Params("email", "password")) }

        verify(repository).logInUser("email", "password")
        verifyNoMoreInteractions(repository)
        repository.logInUser("email", "password").isLeft shouldBe true
    }
}