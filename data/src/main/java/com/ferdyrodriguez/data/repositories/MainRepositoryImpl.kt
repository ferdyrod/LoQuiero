package com.ferdyrodriguez.data.repositories

import com.ferdyrodriguez.data.dataSource.LocalDataSource
import com.ferdyrodriguez.data.dataSource.RemoteDataSource
import com.ferdyrodriguez.domain.MainRepository
import com.ferdyrodriguez.domain.exceptions.Failure
import com.ferdyrodriguez.domain.fp.Either
import com.ferdyrodriguez.domain.fp.flatMap
import com.ferdyrodriguez.domain.models.AuthUser
import com.ferdyrodriguez.domain.models.Product
import com.ferdyrodriguez.domain.models.RegisterUser
import java.io.File

class MainRepositoryImpl constructor(private val remoteDataSource: RemoteDataSource,
                                     private val localDataSource: LocalDataSource): MainRepository {

    override fun getAuthToken(): Either<Failure, String> =
        localDataSource.getAuthToken()

    override fun refreshToken(): Either<Failure, AuthUser> =
        localDataSource.getRefreshToken().flatMap { token ->
            remoteDataSource.refreshToken(token).flatMap { authUser ->
                localDataSource.setAuthUser(authUser).flatMap {
                    Either.Right(authUser)
                }
            }
        }

    override fun verifyToken(): Either<Failure, Map<String, String>> =
        localDataSource.getAuthToken().flatMap { token ->
            remoteDataSource.verifyToken(token)
        }


    override fun registerUser(email: String, password: String): Either<Failure, RegisterUser> =
            remoteDataSource.registerUser(email, password).flatMap {registerUser ->
                logInUser(email, password).flatMap {
                    Either.Right(registerUser)
                }
            }

    override fun logInUser(email: String, password: String): Either<Failure, AuthUser> =
            remoteDataSource.logInUser(email, password).flatMap { authUser ->
                localDataSource.setAuthUser(authUser).flatMap {
                    Either.Right(authUser)
                }
            }

    override fun addProduct(title: String, description: String?, price: Int, mediaFile: File):
            Either<Failure, Product> = remoteDataSource.addProduct(title, description, price, mediaFile)
}