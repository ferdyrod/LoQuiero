package com.ferdyrodriguez.data.local

import com.ferdyrodriguez.data.dataSource.LocalDataSource
import com.ferdyrodriguez.domain.exceptions.Failure
import com.ferdyrodriguez.domain.fp.Either
import com.ferdyrodriguez.domain.models.AuthUser

class LocalDataSourceImpl(private val prefs: PreferenceHelper): LocalDataSource {

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
            Either.Right(Unit)
        } catch (ex:Exception) {
            Either.Left(Failure.LocalError())
        }
    }
}