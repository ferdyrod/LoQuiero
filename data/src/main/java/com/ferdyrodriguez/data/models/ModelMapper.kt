package com.ferdyrodriguez.data.models

import com.ferdyrodriguez.domain.models.*

class ModelMapper {

    fun RegisterUserToDomain(entity: RegisterUserEntity): RegisterUser = RegisterUser(
        entity.id,
        entity.email
    )

    fun AuthUserToDomain(entity: AuthUserEntity): AuthUser = AuthUser(
        entity.refresh,
        entity.access,
        entity.user_id,
        entity.email
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
    fun emptyAuthUser(): AuthUserEntity = AuthUserEntity("", "", 0, "")
    fun emptyProduct(): ProductEntity = ProductEntity(0,0, "", "", "", "", false, 100, "", "", "", null)
    fun emptyUserProfile(): UserProfileEntity = UserProfileEntity(0, "", "", "", null, "", "", null, "")

    private fun transactionToDomain(entity: TransactionEntity) : Transaction = Transaction(
        entity.buyer,
        entity.result,
        entity.created
    )

    fun userProfileToDomain(entity: UserProfileEntity): UserProfile = UserProfile(
        entity.user_id,
        entity.email,
        entity.first_name,
        entity.last_name,
        entity.postal_code,
        entity.phone,
        entity.ip,
        entity.image,
        entity.stripe_id
    )



}