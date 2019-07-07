package com.ferdyrodriguez.domain.usecases

import com.ferdyrodriguez.domain.MainRepository
import com.ferdyrodriguez.domain.exceptions.Failure
import com.ferdyrodriguez.domain.fp.Either
import com.ferdyrodriguez.domain.models.Product
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
class ChargeCreditCardTest : AutoCloseKoinTest() {

    val modules = module(override = true) {
        single { ChargeCreditCard(repository) }
    }

    @Mock
    private lateinit var repository: MainRepository
    private val useCase: ChargeCreditCard by inject()

    @Before
    fun setUp() {
        startKoin { listOf(modules) }
    }

    @Test
    fun `should chage credit card successfully`() {
        loadKoinModules(modules)
        given { repository.chargeCreditCard(0, "") } willReturn {
            Either.Right(
                Product(
                    0,
                    0,
                    "",
                    "",
                    "",
                    "",
                    false,
                    0,
                    "",
                    "",
                    "",
                    null
                )
            )
        }

        runBlocking { useCase.run(ChargeCreditCard.Params(0, "")) }

        verify(repository).chargeCreditCard(0, "")
        verifyNoMoreInteractions(repository)
        repository.chargeCreditCard(0, "").isRight shouldBe true
    }

    @Test
    fun `should return error when there is a problem`() {
        loadKoinModules(modules)

        given { repository.chargeCreditCard(0, "") } willReturn { Either.Left(Failure.ServerError()) }

        runBlocking { useCase.run(ChargeCreditCard.Params(0, "")) }

        verify(repository).chargeCreditCard(0, "")
        verifyNoMoreInteractions(repository)
        repository.chargeCreditCard(0, "").isLeft shouldBe true
    }
}