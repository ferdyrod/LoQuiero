package com.ferdyrodriguez.domain.usecases.base

import com.ferdyrodriguez.domain.exceptions.Failure
import com.ferdyrodriguez.domain.fp.Either
import kotlinx.coroutines.*


abstract class UseCase<out Type, in Params> where Type : Any {

    private val mainJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + mainJob)

    abstract suspend fun run(params: Params): Either<Failure, Type>

    operator fun invoke(params: Params, onResult: (Either<Failure, Type>) -> Unit = {}) =
        uiScope.launch {
            onResult(withContext(Dispatchers.IO)
                { run(params) }
            )
        }

    fun cancel() {
        mainJob.cancel()
    }

    class None
}