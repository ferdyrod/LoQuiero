package com.ferdyrodriguez.data.remote

import com.ferdyrodriguez.data.dataSource.RemoteDataSource
import com.ferdyrodriguez.data.models.ApiResponse
import com.ferdyrodriguez.data.models.ModelMapper
import com.ferdyrodriguez.data.models.ProductEntity
import com.ferdyrodriguez.domain.exceptions.Failure
import com.ferdyrodriguez.domain.fp.Either
import com.ferdyrodriguez.domain.fp.flatMap
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.willReturn
import org.amshove.kluent.should
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
class ProductsRemoteDataSourceTest : AutoCloseKoinTest() {

    val modules = module {
        factory<RemoteDataSource> { RemoteDataSourceImpl(service, mapper) }
    }

    private val remoteDataSource: RemoteDataSource by inject()

    @Mock
    private lateinit var service: ApiService
    @Mock
    private lateinit var mapper: ModelMapper
    @Mock
    private lateinit var response: Response<ApiResponse<List<ProductEntity>>>
    @Mock
    private lateinit var call: Call<ApiResponse<List<ProductEntity>>>

    @Before
    fun setUp() {
        startKoin { modules }
    }

    @Test
    fun `should return list of products from dataSource`() {
        loadKoinModules(modules)
        val productList = listOf(mapper.emptyProduct(), mapper.emptyProduct())
        val rightResponse = ApiResponse(productList)

        given { response.body() } willReturn { rightResponse }
        given { response.isSuccessful } willReturn { true }
        given { call.execute() } willReturn { response }
        given { service.getProducts(null) } willReturn { call }

        val products = remoteDataSource.getProducts(null, false)
        verify(service).getProducts(null)
        products.isRight shouldBe true
        products.either({},{
            it.size shouldBe 2;
        })
    }

    @Test
    fun `should return serverError from call`(){
        loadKoinModules(modules)

        given { response.isSuccessful } willReturn { false }
        given { call.execute() } willReturn { response }
        given { service.getProducts(null)} willReturn { call }

        val products = remoteDataSource.getProducts(null, false)
        verify(service).getProducts(null)
        products.isLeft shouldBe true
        products.either({ failure ->
            failure shouldBeInstanceOf Failure.ServerError::class.java
        }, {})
    }
}