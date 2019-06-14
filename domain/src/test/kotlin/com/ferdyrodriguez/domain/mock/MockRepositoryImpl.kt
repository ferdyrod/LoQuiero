package com.ferdyrodriguez.domain.mock

import com.ferdyrodriguez.domain.MainRepository
import com.ferdyrodriguez.domain.exceptions.Failure
import com.ferdyrodriguez.domain.fp.Either
import com.ferdyrodriguez.domain.models.AuthUser
import com.ferdyrodriguez.domain.models.Product
import com.ferdyrodriguez.domain.models.RegisterUser
import java.io.File

class MockRepositoryImpl : MainRepository {

    override fun getAuthToken(): Either<Failure, String> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun registerUser(email: String, password: String): Either<Failure, RegisterUser> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun logInUser(email: String, password: String): Either<Failure, AuthUser> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun verifyToken(): Either<Failure, Map<String, String>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun refreshToken(): Either<Failure, AuthUser> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addProduct(product: String, description: String?, price: Int, mediaFile: File):
            Either<Failure, Product> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}