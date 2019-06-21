package com.ferdyrodriguez.domain

import com.ferdyrodriguez.domain.exceptions.Failure
import com.ferdyrodriguez.domain.fp.Either
import com.ferdyrodriguez.domain.models.AuthUser
import com.ferdyrodriguez.domain.models.Product
import com.ferdyrodriguez.domain.models.RegisterUser
import com.ferdyrodriguez.domain.models.UserProfile
import java.io.File

interface MainRepository {

    fun getAuthToken(): Either<Failure, String>
    fun refreshToken(): Either<Failure, AuthUser>
    fun verifyToken(): Either<Failure, Map<String, String>>
    fun registerUser(email: String, password: String): Either<Failure, RegisterUser>
    fun logInUser(email: String, password: String): Either<Failure, AuthUser>
    fun addProduct(title: String, description: String?, price: Int, mediaFile: File): Either<Failure, Product>
    fun getProducts(search: String?, ofUser: Boolean): Either<Failure, List<Product>>
    fun deleteProduct(id: Int): Either<Failure, Unit>
    fun saveUserProfile(
        firstName: String?,
        lastName: String?,
        postalCode: Int?,
        phone: String?,
        photo: File?
    ): Either<Failure, UserProfile>
    fun chargeCreditCard(productId: Int, cardToken: String): Either<Failure, Product>

}