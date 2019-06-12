package com.ferdyrodriguez.data.dataSource

import com.ferdyrodriguez.domain.exceptions.Failure
import com.ferdyrodriguez.domain.fp.Either
import com.ferdyrodriguez.domain.models.AuthUser

interface LocalDataSource {

    fun getAuthToken(): Either<Failure, String>
    fun getRefreshToken(): Either<Failure, String>
    fun setAuthUser(authUser: AuthUser): Either<Failure, Unit>
}