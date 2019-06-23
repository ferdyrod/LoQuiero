package com.ferdyrodriguez.domain.usecases

import com.ferdyrodriguez.domain.MainRepository
import com.ferdyrodriguez.domain.exceptions.Failure
import com.ferdyrodriguez.domain.fp.Either
import com.ferdyrodriguez.domain.models.UserProfile
import com.ferdyrodriguez.domain.usecases.base.UseCase
import java.io.File

class SaveUserProfileUseCase(private val repository: MainRepository) :
    UseCase<UserProfile, SaveUserProfileUseCase.Params>() {

    override suspend fun run(params: Params): Either<Failure, UserProfile> =
        repository.saveUserProfile(params.firstName, params.lastName, params.postalCode, params.phone, params.photo)

    data class Params(
        val firstName: String?,
        val lastName: String?,
        val postalCode: Int?,
        val phone: String?,
        val photo: File?
    )
}