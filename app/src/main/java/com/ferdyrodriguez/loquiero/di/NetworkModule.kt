package com.ferdyrodriguez.loquiero.di

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
            .baseUrl(getProperty("baseUrl") as String)
            .client(get())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    single {
        val headerLogInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
        val bodyLogInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
        val headersInterceptor = Interceptor {
            val requestBuilder = it.request().newBuilder()
                .addHeader("Bearer", get())
                .build()
            it.proceed(requestBuilder)
        }


        val client = OkHttpClient.Builder()
        headerLogInterceptor.level = HttpLoggingInterceptor.Level.HEADERS
        bodyLogInterceptor.level = HttpLoggingInterceptor.Level.BODY

        client.addInterceptor(headersInterceptor)
        client.addInterceptor(headerLogInterceptor)
        client.addInterceptor(bodyLogInterceptor)

        client.build()
    }
}