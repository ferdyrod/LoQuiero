package com.ferdyrodriguez.data.local

import com.ferdyrodriguez.data.dataSource.LocalDataSource
import com.ferdyrodriguez.domain.exceptions.Failure
import com.ferdyrodriguez.domain.fp.Either
import com.ferdyrodriguez.domain.models.AuthUser

class LocalDataSourceImpl(private val prefs: PreferenceHelper) : LocalDataSource {

    override fun getAuthToken(): Either<Failure, String> {
        val token = prefs.getPreference(PreferenceConstants.AUTH_TOKEN, "")
        return if (!token.isNullOrEmpty()) Either.Right(token) else Either.Left(Failure.LocalError())
    }

    override fun getRefreshToken(): Either<Failure, String> {
        val token = prefs.getPreference(PreferenceConstants.REFRESH_TOKEN, "")
        return if (!token.isNullOrEmpty()) Either.Right(token) else Either.Left(Failure.LocalError())
    }

    override fun setAuthUser(authUser: AuthUser): Either<Failure, Unit> {
        return try {
            prefs.setPreference(PreferenceConstants.AUTH_TOKEN, authUser.access)
            prefs.setPreference(PreferenceConstants.REFRESH_TOKEN, authUser.refresh)
            authUser.user_id?.let { prefs.setPreference(PreferenceConstants.USER_ID, it) }
            authUser.email?.let {prefs.setPreference(PreferenceConstants.USER_EMAIL, it) }
            Either.Right(Unit)
        } catch (ex: Exception) {
            Either.Left(Failure.LocalError())
        }
    }

    override fun getUserId(): Either<Failure, Int> {
        return try {
            Either.Right(prefs.getPreference(PreferenceConstants.USER_ID, -1) ?: -1)
        } catch (ex: Exception) {
            Either.Left(Failure.LocalError())
        }
    }

    override fun saveUserProfile(
        firstName: String?,
        lastName: String?,
        postalCode: Int?,
        phone: String?,
        photo: String?
    ): Either<Failure, Unit> {
        return try {
            firstName?.let { prefs.setPreference(PreferenceConstants.USER_FIRST_NAME, it) }
            lastName?.let { prefs.setPreference(PreferenceConstants.USER_LAST_NAME, it) }
            postalCode?.let { prefs.setPreference(PreferenceConstants.USER_POSTAL_CODE, it) }
            phone?.let { prefs.setPreference(PreferenceConstants.USER_PHONE, it) }
            photo?.let { prefs.setPreference(PreferenceConstants.USER_PHOTO, it) }
            Either.Right(Unit)
        } catch (ex: Exception){
            Either.Left(Failure.LocalError())
        }
    }
}