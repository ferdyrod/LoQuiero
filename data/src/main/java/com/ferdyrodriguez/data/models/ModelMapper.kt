package com.ferdyrodriguez.data.models

import com.ferdyrodriguez.domain.models.RegisterUser

class ModelMapper {

    fun RegisterUserToDomain(entity: RegisterUserEntity): RegisterUser = RegisterUser(
        entity.id,
        entity.email
    )


    fun RegisterUserToEntity(model: RegisterUser) : RegisterUserEntity = RegisterUserEntity(
        model.id,
        model.email
    )

    fun emptyRegisterUsed(): RegisterUserEntity = RegisterUserEntity(0, "")
}