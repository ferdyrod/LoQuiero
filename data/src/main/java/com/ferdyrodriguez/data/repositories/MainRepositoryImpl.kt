package com.ferdyrodriguez.data.repositories

import com.ferdyrodriguez.data.dataSource.RemoteDataSource
import com.ferdyrodriguez.domain.MainRepository
import com.ferdyrodriguez.domain.exceptions.Failure
import com.ferdyrodriguez.domain.fp.Either
import com.ferdyrodriguez.domain.models.RegisterUser

class MainRepositoryImpl constructor(private val remoteDataSource: RemoteDataSource): MainRepository {

    override fun getAuthToken(): Either<Failure, String> {
        return Either.Right("Token Here")
    }

    override fun registerUser(email: String, password: String): Either<Failure, RegisterUser> =
            remoteDataSource.registerUser(email, password)
}