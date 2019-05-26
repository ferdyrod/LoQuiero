package com.ferdyrodriguez.domain.usecases.base

import com.ferdyrodriguez.domain.fp.Either
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test

class UseCaseTest {

    private val TYPE_TEST = "Test"
    private val TYPE_PARAM = "ParamTest"

    data class MyType(val name: String)
    data class MyParams(val name: String)

    private lateinit var useCase: UseCase<MyType, MyParams>

    @Before
    fun setUp() {
        /* Given */
        useCase = mock { onBlocking { run(any()) } doReturn Either.Right(MyType(TYPE_TEST)) }
    }

    @Test
    fun `running use case should return a Either of use case type`(){

        val params = MyParams(TYPE_PARAM)
        val result = runBlocking { useCase.run(params) }
        whenever(runBlocking { useCase.run(params) })
            .then {
                result shouldEqual Either.Right(MyType(TYPE_TEST))
            }
    }


}