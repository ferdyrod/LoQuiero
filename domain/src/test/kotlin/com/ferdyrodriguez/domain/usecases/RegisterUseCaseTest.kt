package com.ferdyrodriguez.domain.usecases

import com.ferdyrodriguez.domain.MainRepository
import com.ferdyrodriguez.domain.exceptions.Failure
import com.ferdyrodriguez.domain.fp.Either
import com.ferdyrodriguez.domain.mock.MockRepositoryImpl
import com.ferdyrodriguez.domain.models.RegisterUser
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.willReturn
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBe
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.mock.declareMock
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class RegisterUseCaseTest : KoinTest {

    val modules = module(override = true) {
        single { MockRepositoryImpl() as MainRepository }
        single { RegisterUserUseCase(get()) }
    }

    private val repository: MainRepository by inject()
    private val useCase: RegisterUserUseCase by inject()

    @Before
    fun setUp() {
        startKoin { listOf(modules)}
    }

    @After
    fun tearDown() {
        stopKoin()
    }


    @Test
    fun `should register user succesfully`() {
        loadKoinModules(modules)
        declareMock<MainRepository> {
            given { this.registerUser("email", "password") } willReturn { Either.Right(RegisterUser(0, "email")) }
        }

        runBlocking { useCase.run(RegisterUserUseCase.Params("email", "password")) }

        verify(repository).registerUser("email", "password")
        verifyNoMoreInteractions(repository)
        repository.registerUser("email", "password").isRight shouldBe true
    }

    @Test
    fun `should gives error when there was a problem`(){
        loadKoinModules(modules)
        declareMock<MainRepository> {
            given { this.registerUser("email", "password") } willReturn { Either.Left(Failure.ServerError()) }
        }

        runBlocking { useCase.run(RegisterUserUseCase.Params("email", "password")) }

        verify(repository).registerUser("email", "password")
        verifyNoMoreInteractions(repository)
        repository.registerUser("email", "password").isLeft shouldBe true
    }
}