package com.ferdyrodriguez.data.dataSource

import com.ferdyrodriguez.domain.exceptions.Failure
import com.ferdyrodriguez.domain.fp.Either
import com.ferdyrodriguez.domain.models.AuthUser
import com.ferdyrodriguez.domain.models.RegisterUser

interface RemoteDataSource {

    fun registerUser(email: String, password: String): Either<Failure, RegisterUser>
    fun logInUser(email: String, password: String): Either<Failure, AuthUser>
}