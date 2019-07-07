package com.ferdyrodriguez.domain.usecases

import com.ferdyrodriguez.domain.MainRepository
import com.ferdyrodriguez.domain.exceptions.Failure
import com.ferdyrodriguez.domain.fp.Either
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
class GetProductUseCaseTest: AutoCloseKoinTest(){
    val modules = module(override = true) {
        single { GetProductsUseCase(repository) }
    }

    @Mock
    private lateinit var repository: MainRepository
    private val useCase: GetProductsUseCase by inject()

    @Before
    fun setUp() {
        startKoin { listOf(modules)}
    }


    @Test
    fun `should get product successfully`() {
        loadKoinModules(modules)
        given { repository.getProducts(null, false) } willReturn { Either.Right(emptyList()) }

        runBlocking { useCase.run(GetProductsUseCase.Params(null, false)) }

        verify(repository).getProducts(null, false)
        verifyNoMoreInteractions(repository)
        repository.getProducts(null, false).isRight shouldBe true
    }

    @Test
    fun `should return error when there is a problem`(){
        loadKoinModules(modules)

        given { repository.getProducts(null, false) } willReturn { Either.Left(Failure.ServerError()) }

        runBlocking { useCase.run(GetProductsUseCase.Params(null, false)) }

        verify(repository).getProducts(null, false)
        verifyNoMoreInteractions(repository)
        repository.getProducts(null, false).isLeft shouldBe true
    }
}