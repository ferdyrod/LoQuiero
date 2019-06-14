package com.ferdyrodriguez.loquiero.di

import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.ferdyrodriguez.data.remote.ApiService
import com.ferdyrodriguez.domain.MainRepository
import com.ferdyrodriguez.domain.fp.map
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.net.HttpURLConnection
import java.util.concurrent.TimeUnit


val networkModule: Module = module {

    single {
        Retrofit.Builder()
            .baseUrl("http://192.168.1.38:5000/api/")
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
        val authenticator = Authenticator{ route, response ->
            var responseCount = 0
            if(response.code() == HttpURLConnection.HTTP_UNAUTHORIZED){
                responseCount += 1
                if(responseCount < 3) {
                    val tokenEither = mainRepository.refreshToken()
                    if (tokenEither.isLeft) {
                        val intent = Intent("login_required")
                        intent.putExtra("message", "login_required")
                        LocalBroadcastManager.getInstance(get()).sendBroadcast(intent)
                    }
                }
            }
            val newToken = mainRepository.refreshToken().either({}) { token -> token.access} as String
            response.request().newBuilder().addHeader("Authorization", "Bearer $newToken").build()
        }


        val client = OkHttpClient.Builder()
        headerLogInterceptor.level = HttpLoggingInterceptor.Level.HEADERS
        bodyLogInterceptor.level = HttpLoggingInterceptor.Level.BODY

        client.addInterceptor(headersInterceptor)
        client.addInterceptor(headerLogInterceptor)
        client.addInterceptor(bodyLogInterceptor)
        client.authenticator(authenticator)
        client.writeTimeout(20, TimeUnit.SECONDS)
        client.build() as OkHttpClient
    }

    factory { ApiService(get()) }
}