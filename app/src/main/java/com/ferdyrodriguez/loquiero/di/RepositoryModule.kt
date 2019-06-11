package com.ferdyrodriguez.loquiero.di

import com.ferdyrodriguez.data.dataSource.RemoteDataSource
import com.ferdyrodriguez.data.models.ModelMapper
import com.ferdyrodriguez.data.remote.RemoteDataSourceImpl
import com.ferdyrodriguez.data.repositories.MainRepositoryImpl
import com.ferdyrodriguez.domain.MainRepository
import com.ferdyrodriguez.domain.usecases.LoginUseCase
import com.ferdyrodriguez.domain.usecases.RegisterUserUseCase
import org.koin.core.module.Module
import org.koin.dsl.module

val repositoryModule: Module = module {
    single { ModelMapper() }
    single { RemoteDataSourceImpl(get(), get()) as RemoteDataSource}
    single { MainRepositoryImpl(get()) as MainRepository }

    factory { LoginUseCase(get()) }
    factory { RegisterUserUseCase(get()) }
}