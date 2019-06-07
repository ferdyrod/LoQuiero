package com.ferdyrodriguez.data.repositories

import com.ferdyrodriguez.data.dataSource.RemoteDataSource
import com.ferdyrodriguez.domain.MainRepository
import com.ferdyrodriguez.domain.exceptions.Failure
import com.ferdyrodriguez.domain.fp.Either
import com.ferdyrodriguez.domain.models.RegisterUser
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
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
import com.nhaarman.mockitokotlin2.given as given1

@RunWith(MockitoJUnitRunner::class)
class RepositoryTests : AutoCloseKoinTest() {

    val modules = module {
        single<MainRepository> { MainRepositoryImpl(remoteDataSource) }
    }

    @Mock
    private lateinit var remoteDataSource: RemoteDataSource
    private val repository: MainRepository by inject()

    private val emptyRegisterUser = RegisterUser(0, "")
    private val emptyEither: Either<Failure, RegisterUser> = Either.Right(emptyRegisterUser)

    @Before
    fun setUp() {
        startKoin { modules }
    }


    @Test
    fun `should register user successfully`() {
        loadKoinModules(modules)
        given { remoteDataSource.registerUser("email", "password") }.willReturn(emptyEither)

        val registeredUser = repository.registerUser("email", "password")

        verify(remoteDataSource).registerUser("email", "password")
        verifyNoMoreInteractions(remoteDataSource)
        registeredUser.isRight shouldBe true
        registeredUser shouldEqual emptyEither
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


}

