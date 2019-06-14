package com.ferdyrodriguez.loquiero.di

import com.ferdyrodriguez.data.dataSource.LocalDataSource
import com.ferdyrodriguez.data.dataSource.RemoteDataSource
import com.ferdyrodriguez.data.local.LocalDataSourceImpl
import com.ferdyrodriguez.data.models.ModelMapper
import com.ferdyrodriguez.data.remote.RemoteDataSourceImpl
import com.ferdyrodriguez.data.repositories.MainRepositoryImpl
import com.ferdyrodriguez.domain.MainRepository
import com.ferdyrodriguez.domain.usecases.*
import org.koin.core.module.Module
import org.koin.dsl.module

val repositoryModule: Module = module {
    single { ModelMapper() }
    single { RemoteDataSourceImpl(get(), get()) as RemoteDataSource}
    single { LocalDataSourceImpl(get()) as LocalDataSource }
    single { MainRepositoryImpl(get(), get()) as MainRepository }

    factory { LoginUseCase(get()) }
    factory { VerifyUseCase(get()) }
    factory { RegisterUserUseCase(get()) }
    factory { AddProductUseCase(get()) }
    factory { GetProductsUseCase(get()) }
}