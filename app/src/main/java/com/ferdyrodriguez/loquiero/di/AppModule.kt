package com.ferdyrodriguez.loquiero.di

import android.content.SharedPreferences
import android.preference.PreferenceManager
import org.koin.core.module.Module
import org.koin.dsl.module


val appModule: Module = module {

    factory<SharedPreferences> {
        PreferenceManager.getDefaultSharedPreferences(get())
    }

}