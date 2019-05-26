package com.ferdyrodriguez.domain

import com.ferdyrodriguez.domain.exceptions.Failure
import com.ferdyrodriguez.domain.fp.Either

interface MainRepository {


    fun getAuthToken(): Either<Failure, String>

}