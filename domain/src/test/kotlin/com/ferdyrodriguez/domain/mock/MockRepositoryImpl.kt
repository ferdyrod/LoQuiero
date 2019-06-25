package com.ferdyrodriguez.domain.mock

import com.ferdyrodriguez.domain.MainRepository
import com.ferdyrodriguez.domain.exceptions.Failure
import com.ferdyrodriguez.domain.fp.Either
import com.ferdyrodriguez.domain.models.AuthUser
import com.ferdyrodriguez.domain.models.Product
import com.ferdyrodriguez.domain.models.RegisterUser
import com.ferdyrodriguez.domain.models.UserProfile
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

    override fun getProducts(search: String?, ofUser: Boolean): Either<Failure, List<Product>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteProduct(id: Int): Either<Failure, Unit> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveUserProfile(
        firstName: String?,
        lastName: String?,
        postalCode: Int?,
        phone: String?,
        photo: File?
    ): Either<Failure, UserProfile> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun chargeCreditCard(productId: Int, cardToken: String): Either<Failure, Product> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}