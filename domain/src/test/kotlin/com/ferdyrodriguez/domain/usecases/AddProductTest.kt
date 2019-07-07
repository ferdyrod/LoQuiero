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
import java.io.File


@RunWith(MockitoJUnitRunner::class)
class AddProductTest : AutoCloseKoinTest() {

    private val modules = module(override = true) {
        single { AddProductUseCase(repository) }
    }

    @Mock private lateinit var repository: MainRepository
    private val useCase: AddProductUseCase by inject()
    private val product  = Product(0, 0, "title", "description", "BUY", "PENDING", true, 1000, "", "", "",null)
    private val file = File("")

    @Before
    fun setUp() {
        startKoin { listOf(modules) }
    }


    @Test
    fun `should add product successfully`() {
        loadKoinModules(modules)
        given { repository.addProduct(product.title, product.description, product.price, file) } willReturn { Either.Right(product)}

        runBlocking { useCase.run(AddProductUseCase.Params(
            product.title,
            product.description,
            product.price,
            file
        )) }

        verify(repository).addProduct(product.title, product.description, product.price, file)
        verifyNoMoreInteractions(repository)
        repository.addProduct(product.title, product.description, product.price, file).isRight shouldBe true
    }

    @Test
    fun `should return error when there is a problem`() {
        loadKoinModules(modules)

        given { repository.addProduct(product.title, product.description, product.price, file) }
            .willReturn { Either.Left(Failure.ServerError()) }

        runBlocking { useCase.run(AddProductUseCase.Params(
            product.title,
            product.description,
            product.price,
            file
        )) }
        verify(repository).addProduct(product.title, product.description, product.price, file)
        verifyNoMoreInteractions(repository)
        repository.addProduct(product.title, product.description, product.price, file).isLeft shouldBe true
    }
}