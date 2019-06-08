package com.ferdyrodriguez.loquiero.di

import android.preference.PreferenceManager
import com.ferdyrodriguez.loquiero.utils.PreferenceHelper
import org.koin.core.module.Module
import org.koin.dsl.module


val appModule: Module = module {

    single {
        PreferenceManager.getDefaultSharedPreferences(get())
    }
    single { PreferenceHelper(get()) }

}