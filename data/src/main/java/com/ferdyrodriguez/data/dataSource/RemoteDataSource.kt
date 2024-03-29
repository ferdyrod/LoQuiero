package com.ferdyrodriguez.data.dataSource

import com.ferdyrodriguez.domain.exceptions.Failure
import com.ferdyrodriguez.domain.fp.Either
import com.ferdyrodriguez.domain.models.AuthUser
import com.ferdyrodriguez.domain.models.Product
import com.ferdyrodriguez.domain.models.RegisterUser
import com.ferdyrodriguez.domain.models.UserProfile
import java.io.File

interface RemoteDataSource {

    fun registerUser(email: String, password: String): Either<Failure, RegisterUser>
    fun logInUser(email: String, password: String): Either<Failure, AuthUser>
    fun verifyToken(token: String): Either<Failure, Map<String, String>>
    fun refreshToken(token: String): Either<Failure, AuthUser>
    fun addProduct(title: String, description: String?, price: Int, mediaFile: File): Either<Failure, Product>
    fun getProducts(search: String?, ofUser: Boolean): Either<Failure, List<Product>>
    fun deleteProduct(id: Int): Either<Failure, Unit>
    fun saveUserProfile(
        id: Int,
        firstName: String?,
        lastName: String?,
        postalCode: Int?,
        phone: String?,
        photo: File?
    ): Either<Failure, UserProfile>
    fun chargeCreditCard(productId: Int, cardToken: String): Either<Failure, Product>
    fun getUser(user_id: Int?): Either<Failure, UserProfile>
}