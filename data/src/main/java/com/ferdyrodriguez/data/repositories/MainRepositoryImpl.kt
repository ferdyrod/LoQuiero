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
import com.ferdyrodriguez.domain.models.UserProfile
import java.io.File

class MainRepositoryImpl constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : MainRepository {

    override fun getAuthToken(): Either<Failure, String> =
        localDataSource.getAuthToken()

    override fun refreshToken(): Either<Failure, AuthUser> =
        localDataSource.getRefreshToken().flatMap { token: String ->
            remoteDataSource.refreshToken(token).flatMap { authUser: AuthUser ->
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
        remoteDataSource.registerUser(email, password).flatMap { registerUser ->
            logInUser(email, password).flatMap {
                Either.Right(registerUser)
            }
        }

    override fun logInUser(email: String, password: String): Either<Failure, AuthUser> =
        remoteDataSource.logInUser(email, password).flatMap { authUser ->
            localDataSource.setAuthUser(authUser).flatMap {
                remoteDataSource.getUser(authUser.user_id).flatMap { it: UserProfile ->
                    localDataSource.saveUserProfile(it.firstName, it.lastName, it.postalCode, it.phone, it.photo)
                    Either.Right(authUser)
                }
            }
        }

    override fun addProduct(title: String, description: String?, price: Int, mediaFile: File):
            Either<Failure, Product> = remoteDataSource.addProduct(title, description, price, mediaFile)

    override fun getProducts(search: String?, ofUser: Boolean): Either<Failure, List<Product>> =
        remoteDataSource.getProducts(search, ofUser)

    override fun deleteProduct(id: Int): Either<Failure, Unit> =
        remoteDataSource.deleteProduct(id)

    override fun saveUserProfile(
        firstName: String?,
        lastName: String?,
        postalCode: Int?,
        phone: String?,
        photo: File?
    ): Either<Failure, UserProfile> =
        localDataSource.getUserId().flatMap {
            remoteDataSource.saveUserProfile(it, firstName, lastName, postalCode, phone, photo).flatMap { profile ->
                localDataSource.saveUserProfile(profile.firstName, profile.lastName, profile.postalCode, profile.phone, profile.photo).flatMap {
                    Either.Right(profile)
                }
            }
        }

    override fun chargeCreditCard(productId: Int, cardToken: String): Either<Failure, Product> =
            remoteDataSource.chargeCreditCard(productId, cardToken)
}