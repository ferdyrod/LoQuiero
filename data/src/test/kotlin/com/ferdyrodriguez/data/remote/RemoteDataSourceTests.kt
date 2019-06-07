package com.ferdyrodriguez.data.remote

import com.ferdyrodriguez.data.dataSource.RemoteDataSource
import com.ferdyrodriguez.data.models.ApiResponse
import com.ferdyrodriguez.data.models.ModelMapper
import com.ferdyrodriguez.data.models.RegisterUserEntity
import com.ferdyrodriguez.data.models.dto.RegisterUserDto
import com.ferdyrodriguez.domain.exceptions.Failure
import com.ferdyrodriguez.domain.fp.Either
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.verify
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
import retrofit2.Call
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class RemoteDataSourceTests: AutoCloseKoinTest() {

    val modules = module {
        factory<RemoteDataSource> { RemoteDataSourceImpl(service, mapper) }
    }

    private val remoteDataSource: RemoteDataSource by inject()

    @Mock private lateinit var service: ApiService
    @Mock private lateinit var mapper: ModelMapper
    @Mock private lateinit var response: Response<ApiResponse<RegisterUserEntity>>
    @Mock private lateinit var call: Call<ApiResponse<RegisterUserEntity>>

    @Before
    fun setUp() {
        startKoin { modules }
    }

    @Test
    fun `should return registered user from repository`(){
        loadKoinModules(modules)
        val userDto = RegisterUserDto("email", "password")
        val rightResponse = ApiResponse(200,"", RegisterUserEntity(0, ""))

        given { response.body() } willReturn { rightResponse }
        given { response.isSuccessful } willReturn { true}
        given { call.execute() } willReturn { response }
        given { service.registerUser(userDto)} willReturn { call }

        val user = remoteDataSource.registerUser("email", "password")
        verify(service).registerUser(userDto)
        user.isRight shouldBe true
        user shouldEqual Either.Right(mapper.RegisterUserToDomain(mapper.emptyRegisterUsed()))
    }

    @Test
    fun `should return error when email is already registered`(){
        loadKoinModules(modules)
        val userDto = RegisterUserDto("email", "password")

        given { response.isSuccessful } willReturn { false }
        given { call.execute() } willReturn { response }
        given { service.registerUser(userDto)} willReturn { call }

        val user = remoteDataSource.registerUser("email", "password")
        verify(service).registerUser(userDto)
        user.isLeft shouldBe true
        user.either({ failure ->
            failure shouldBeInstanceOf Failure.ServerError::class.java
        }, {})
    }


    @Test
    fun `should return serverError from call`(){
        loadKoinModules(modules)
        val userDto = RegisterUserDto("email", "password")

        given { response.isSuccessful } willReturn { false }
        given { call.execute() } willReturn { response }
        given { service.registerUser(userDto)} willReturn { call }

        val user = remoteDataSource.registerUser("email", "password")
        verify(service).registerUser(userDto)
        user.isLeft shouldBe true
        user.either({ failure ->
            failure shouldBeInstanceOf Failure.ServerError::class.java
        }, {})
    }
}