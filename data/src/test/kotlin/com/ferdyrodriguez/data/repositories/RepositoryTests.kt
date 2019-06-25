package com.ferdyrodriguez.data.repositories

import com.ferdyrodriguez.data.*
import com.ferdyrodriguez.data.dataSource.LocalDataSource
import com.ferdyrodriguez.data.dataSource.RemoteDataSource
import com.ferdyrodriguez.domain.MainRepository
import com.ferdyrodriguez.domain.exceptions.Failure
import com.ferdyrodriguez.domain.fp.Either
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.willReturn
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeInstanceOf
import org.amshove.kluent.shouldEqual
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
import com.nhaarman.mockitokotlin2.given as given1

@RunWith(MockitoJUnitRunner::class)
class RepositoryTests : AutoCloseKoinTest() {

    val modules = module {
        single<MainRepository> { MainRepositoryImpl(remoteDataSource, localDataSource) }
    }

    @Mock
    private lateinit var remoteDataSource: RemoteDataSource
    @Mock
    private lateinit var localDataSource: LocalDataSource
    private val repository: MainRepository by inject()
    private val file = File("")


    @Before
    fun setUp() {
        startKoin { modules }
    }


    @Test
    fun `should register user successfully`() {
        loadKoinModules(modules)
        given { localDataSource.saveUserProfile("email", "password", 0, "", "") }.willReturn(Either.Right(Unit))
        given { remoteDataSource.getUser(0) }.willReturn(emptyUserProfileEither)
        given { remoteDataSource.registerUser("email", "password") }.willReturn(emptyRegisterEither)
        given { remoteDataSource.logInUser("email", "password") }.willReturn(emptyAuthEither)
        given { localDataSource.setAuthUser(emptyAuthUser) } willReturn { Either.Right(Unit)}

        val registeredUser = repository.registerUser("email", "password")

        verify(remoteDataSource).registerUser("email", "password")
        verify(remoteDataSource).logInUser("email", "password")
        verifyNoMoreInteractions(remoteDataSource)
        verify(localDataSource).setAuthUser(emptyAuthUser)
        verifyNoMoreInteractions(localDataSource)
        registeredUser.isRight shouldBe true
        registeredUser shouldEqual emptyRegisterEither
    }

    @Test
    fun `should fail registering user`() {
        loadKoinModules(modules)
        given { remoteDataSource.registerUser("email", "password") }.willReturn(Either.Left(Failure.ServerError()))

        val registeredUser = repository.registerUser("email", "password")

        verify(remoteDataSource).registerUser("email", "password")
        verifyNoMoreInteractions(remoteDataSource)
        registeredUser.isLeft shouldBe true
        registeredUser shouldBeInstanceOf Either::class
    }

    @Test
    fun `should login user successfully`() {
        loadKoinModules(modules)
        given { localDataSource.saveUserProfile("email", "password", 0, "", "") }.willReturn(Either.Right(Unit))
        given { remoteDataSource.getUser(0) }.willReturn(emptyUserProfileEither)
        given { localDataSource.setAuthUser(emptyAuthUser) } willReturn { Either.Right(Unit)}
        given { remoteDataSource.logInUser("email", "password") }.willReturn(emptyAuthEither)

        val loggedInUser = repository.logInUser("email", "password")

        verify(remoteDataSource).logInUser("email", "password")
        //verifyNoMoreInteractions(remoteDataSource)
        verify(localDataSource).setAuthUser(emptyAuthUser)
        //verifyNoMoreInteractions(localDataSource)
        loggedInUser.isRight shouldBe true
        loggedInUser shouldEqual emptyAuthEither
    }

    @Test
    fun `should fail login user`() {
        loadKoinModules(modules)
        given { remoteDataSource.logInUser("email", "password") }.willReturn(Either.Left(Failure.ServerError()))

        val loggedInUser = repository.logInUser("email", "password")

        verify(remoteDataSource).logInUser("email", "password")
        verifyNoMoreInteractions(remoteDataSource)
        loggedInUser.isLeft shouldBe true
        loggedInUser shouldBeInstanceOf Either::class
    }


    @Test
    fun `should add product successfully`() {
        loadKoinModules(modules)
        given { remoteDataSource.addProduct(emptyProduct.title, emptyProduct.description, emptyProduct.price, file) }.willReturn(emptyProductEither)

        val product = repository.addProduct(
            emptyProduct.title,
            emptyProduct.description,
            emptyProduct.price,
            file
        )

        verify(remoteDataSource).addProduct(emptyProduct.title, emptyProduct.description, emptyProduct.price, file)
        verifyNoMoreInteractions(remoteDataSource)
        product.isRight shouldBe true
        product shouldEqual emptyProductEither
    }

    @Test
    fun `should fail adding product`() {
        loadKoinModules(modules)
        given { remoteDataSource.addProduct(emptyProduct.title, emptyProduct.description, emptyProduct.price, file) }.willReturn(Either.Left(Failure.ServerError()))

        val product = repository.addProduct(
            emptyProduct.title,
            emptyProduct.description,
            emptyProduct.price,
            file
        )

        verify(remoteDataSource).addProduct(emptyProduct.title, emptyProduct.description, emptyProduct.price, file)
        verifyNoMoreInteractions(remoteDataSource)
        product.isLeft shouldBe true
        product shouldBeInstanceOf Either::class
    }


}

