package com.ferdyrodriguez.data.dataSource

import com.ferdyrodriguez.domain.exceptions.Failure
import com.ferdyrodriguez.domain.fp.Either
import com.ferdyrodriguez.domain.models.AuthUser

interface LocalDataSource {

    fun getAuthToken(): Either<Failure, String>
    fun getRefreshToken(): Either<Failure, String>
    fun setAuthUser(authUser: AuthUser): Either<Failure, Unit>
    fun getUserId(): Either<Failure, Int>
    fun saveUserProfile(
        firstName: String?,
        lastName: String?,
        postalCode: Int?,
        phone: String?,
        photo: String?
    ): Either<Failure, Unit>
}