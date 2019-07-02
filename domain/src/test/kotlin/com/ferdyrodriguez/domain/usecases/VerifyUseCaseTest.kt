package com.ferdyrodriguez.domain.usecases

import com.ferdyrodriguez.domain.MainRepository
import com.ferdyrodriguez.domain.exceptions.Failure
import com.ferdyrodriguez.domain.fp.Either
import com.ferdyrodriguez.domain.usecases.base.UseCase
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
class VerifyUseCaseTest: AutoCloseKoinTest() {
    val modules = module(override = true) {
        single { VerifyUseCase(repository) }
    }

    @Mock
    private lateinit var repository: MainRepository
    private val useCase: VerifyUseCase by inject()

    @Before
    fun setUp() {
        startKoin { listOf(modules)}
    }


    @Test
    fun `should verify token succesfully`() {
        loadKoinModules(modules)
        given { repository.verifyToken() } willReturn { Either.Right(hashMapOf()) }

        runBlocking { useCase.run(UseCase.None()) }

        verify(repository).verifyToken()
        verifyNoMoreInteractions(repository)
        repository.verifyToken().isRight shouldBe true
    }

    @Test
    fun `should return error when there is a problem`(){
        loadKoinModules(modules)

        given { repository.verifyToken() } willReturn { Either.Left(Failure.ServerError()) }

        runBlocking { useCase.run(UseCase.None()) }

        verify(repository).verifyToken()
        verifyNoMoreInteractions(repository)
        repository.verifyToken().isLeft shouldBe true
    }
}