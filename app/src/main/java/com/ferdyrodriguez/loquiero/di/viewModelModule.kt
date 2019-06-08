package com.ferdyrodriguez.loquiero.di

import com.ferdyrodriguez.loquiero.usecases.registration.RegistrationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { RegistrationViewModel(get(), get()) }
}