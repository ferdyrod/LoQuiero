package com.ferdyrodriguez.domain.usecases

import com.ferdyrodriguez.domain.MainRepository
import com.ferdyrodriguez.domain.exceptions.Failure
import com.ferdyrodriguez.domain.fp.Either
import com.ferdyrodriguez.domain.models.RegisterUser
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
class RegisterUseCaseTest : AutoCloseKoinTest() {

    val modules = module(override = true) {
        single { RegisterUserUseCase(repository) }
    }

    @Mock private lateinit var repository: MainRepository
    private val useCase: RegisterUserUseCase by inject()

    @Before
    fun setUp() {
        startKoin { listOf(modules)}
    }


    @Test
    fun `should register user successfully`() {
        loadKoinModules(modules)
        given { repository.registerUser("email", "password") } willReturn { Either.Right(RegisterUser(0, "email")) }

        runBlocking { useCase.run(RegisterUserUseCase.Params("email", "password")) }

        verify(repository).registerUser("email", "password")
        verifyNoMoreInteractions(repository)
        repository.registerUser("email", "password").isRight shouldBe true
    }

    @Test
    fun `should return error when there is a problem`(){
        loadKoinModules(modules)

        given { repository.registerUser("email", "password") } willReturn { Either.Left(Failure.ServerError()) }

        runBlocking { useCase.run(RegisterUserUseCase.Params("email", "password")) }

        verify(repository).registerUser("email", "password")
        verifyNoMoreInteractions(repository)
        repository.registerUser("email", "password").isLeft shouldBe true
    }
}