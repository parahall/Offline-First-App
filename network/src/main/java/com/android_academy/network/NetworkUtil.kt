package com.android_academy.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

suspend fun <T> safeResultApiCall(
    callFunction: suspend () -> Response<T>
): Result<out T> {
    return runCatching {

        val myObject = withContext(Dispatchers.IO) { callFunction.invoke() }

        myObject.getValidatedBody()?.let { value ->
            Result.success(value)
        } ?: run {
            val throwable = NetworkThrowable("Network error: ${myObject.code()}", myObject.code())
            Result.failure<T>(
                exception = throwable
            )
        }

    }.getOrElse { throwable ->
        Result.failure(
            exception = throwable
        )
    }
}

fun <T> Response<T>?.getValidatedBody(): T? {
    return if (this?.isSuccessful == true) {
        this.body()
    } else {
        null
    }
}


class NetworkThrowable(override val message: String?, val errorCode: Int?) : Throwable(message)