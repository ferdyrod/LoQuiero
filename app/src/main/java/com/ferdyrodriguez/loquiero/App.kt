package com.ferdyrodriguez.loquiero

import android.app.Application
import com.facebook.stetho.Stetho
import com.ferdyrodriguez.loquiero.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


class App: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(listOf(appModule, networkModule, repositoryModule, viewModelModule, utilsModule))
        }

        if(BuildConfig.DEBUG)
            Stetho.initializeWithDefaults(this)
    }


}