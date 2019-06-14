package com.ferdyrodriguez.data.models

import com.ferdyrodriguez.domain.models.AuthUser
import com.ferdyrodriguez.domain.models.Product
import com.ferdyrodriguez.domain.models.RegisterUser
import com.ferdyrodriguez.domain.models.Transaction

class ModelMapper {

    fun RegisterUserToDomain(entity: RegisterUserEntity): RegisterUser = RegisterUser(
        entity.id,
        entity.email
    )

    fun AuthUserToDomain(entity: AuthUserEntity): AuthUser = AuthUser(
        entity.refresh,
        entity.access
    )

    fun productToDomain(entity: ProductEntity): Product = Product(
        entity.id,
        entity.user_id,
        entity.title,
        entity.description,
        entity.type,
        entity.status,
        entity.active,
        entity.price,
        entity.image,
        entity.created,
        entity.updated,
        if(entity.transaction != null) transactionToDomain(entity.transaction) else null
    )


    fun emptyRegisterUsed(): RegisterUserEntity = RegisterUserEntity(0, "")
    fun emptyAuthUser(): AuthUserEntity = AuthUserEntity("", "")
    fun emptyProduct(): ProductEntity = ProductEntity(0,0, "", "", "", "", false, 100, "", "", "", null)

    private fun transactionToDomain(entity: TransactionEntity) : Transaction = Transaction(
        entity.buyer,
        entity.result,
        entity.created
    )
}