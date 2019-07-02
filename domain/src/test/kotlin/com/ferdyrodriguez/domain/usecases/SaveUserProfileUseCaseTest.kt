package com.ferdyrodriguez.domain.usecases

import com.ferdyrodriguez.domain.MainRepository
import com.ferdyrodriguez.domain.exceptions.Failure
import com.ferdyrodriguez.domain.fp.Either
import com.ferdyrodriguez.domain.models.UserProfile
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
class SaveUserProfileUseCaseTest: AutoCloseKoinTest() {
    val modules = module(override = true) {
        single { SaveUserProfileUseCase(repository) }
    }

    @Mock
    private lateinit var repository: MainRepository
    private val useCase: SaveUserProfileUseCase by inject()

    @Before
    fun setUp() {
        startKoin { listOf(modules)}
    }


    @Test
    fun `should save user profile succesfully`() {
        loadKoinModules(modules)
        given { repository.saveUserProfile("", "", 0, "", null) } willReturn { Either.Right(UserProfile(0, "", "", "", 0, "", "", "","")) }

        runBlocking { useCase.run(SaveUserProfileUseCase.Params("", "", 0 , "", null)) }

        verify(repository).saveUserProfile("", "", 0, "", null)
        verifyNoMoreInteractions(repository)
        repository.saveUserProfile("", "", 0, "", null).isRight shouldBe true
    }

    @Test
    fun `should return error when there is a problem`(){
        loadKoinModules(modules)

        given { repository.saveUserProfile("", "", 0, "", null) } willReturn { Either.Left(Failure.ServerError()) }

        runBlocking { useCase.run(SaveUserProfileUseCase.Params("", "", 0 , "", null)) }

        verify(repository).saveUserProfile("", "", 0, "", null)
        verifyNoMoreInteractions(repository)
        repository.saveUserProfile("", "", 0, "", null).isLeft shouldBe true
    }
}