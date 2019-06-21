package com.ferdyrodriguez.loquiero.di

import com.ferdyrodriguez.loquiero.usecases.addproduct.AddProductViewModel
import com.ferdyrodriguez.loquiero.usecases.login.LoginViewModel
import com.ferdyrodriguez.loquiero.usecases.loginOrRegistration.LoginOrRegistrationViewModel
import com.ferdyrodriguez.loquiero.usecases.main.MainViewModel
import com.ferdyrodriguez.loquiero.usecases.registration.RegistrationViewModel
import com.ferdyrodriguez.loquiero.usecases.search.SearchViewModel
import com.ferdyrodriguez.loquiero.usecases.splash.SplashViewModel
import com.ferdyrodriguez.loquiero.usecases.userProducts.UserProductViewModel
import com.ferdyrodriguez.loquiero.usecases.userProfile.UserProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { SplashViewModel(get(), get()) }
    viewModel { LoginOrRegistrationViewModel(get()) }
    viewModel { RegistrationViewModel(get(), get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { MainViewModel(get(), get()) }
    viewModel { AddProductViewModel(get()) }
    viewModel { UserProductViewModel(get(), get()) }
    viewModel { SearchViewModel(get()) }
    viewModel { UserProfileViewModel(get(), get()) }
}