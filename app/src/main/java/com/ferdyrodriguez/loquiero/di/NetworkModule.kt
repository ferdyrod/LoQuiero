package com.ferdyrodriguez.loquiero.di

import com.ferdyrodriguez.data.remote.ApiService
import com.ferdyrodriguez.domain.MainRepository
import com.ferdyrodriguez.domain.fp.map
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val networkModule: Module = module {

    single {
        Retrofit.Builder()
            .baseUrl("http://192.168.1.38:8000/api/")
            .client(get())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    single {
        val mainRepository: MainRepository by inject()
        val headerLogInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
        val bodyLogInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
        val headersInterceptor = Interceptor {
            var authToken = ""
            mainRepository.getAuthToken().map { token -> authToken = token }
            val requestBuilder = it.request().newBuilder()
            requestBuilder.addHeader("Bearer", authToken)
            requestBuilder.build()
            it.proceed(requestBuilder.build())
        }


        val client = OkHttpClient.Builder()
        headerLogInterceptor.level = HttpLoggingInterceptor.Level.HEADERS
        bodyLogInterceptor.level = HttpLoggingInterceptor.Level.BODY

        client.addInterceptor(headersInterceptor)
        client.addInterceptor(headerLogInterceptor)
        client.addInterceptor(bodyLogInterceptor)

        client.build() as OkHttpClient
    }

    factory { ApiService(get()) }
}