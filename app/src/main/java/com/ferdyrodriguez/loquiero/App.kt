package com.ferdyrodriguez.loquiero

import android.app.Application
import com.ferdyrodriguez.loquiero.di.appModule
import com.ferdyrodriguez.loquiero.di.networkModule
import com.ferdyrodriguez.loquiero.di.repositoryModule
import com.ferdyrodriguez.loquiero.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


class App: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(listOf(appModule, networkModule, repositoryModule, viewModelModule))
        }
    }


}