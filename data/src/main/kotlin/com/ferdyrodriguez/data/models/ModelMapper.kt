package com.ferdyrodriguez.data.models

import com.ferdyrodriguez.domain.models.AuthUser
import com.ferdyrodriguez.domain.models.RegisterUser

class ModelMapper {

    fun RegisterUserToDomain(entity: RegisterUserEntity): RegisterUser = RegisterUser(
        entity.id,
        entity.email
    )

    fun AuthUserToDomain(entity: AuthUserEntity): AuthUser = AuthUser(
        entity.refreshToken,
        entity.authToken
    )


    fun emptyRegisterUsed(): RegisterUserEntity = RegisterUserEntity(0, "")
    fun emptyAuthUser(): AuthUserEntity = AuthUserEntity("", "")
}