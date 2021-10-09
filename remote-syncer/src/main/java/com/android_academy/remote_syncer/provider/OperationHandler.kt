package com.android_academy.remote_syncer.provider

import android.util.Log
import com.android_academy.remote_syncer.operations.RemoteOperation
import com.android_academy.remote_syncer.models.RemoteResponse

abstract class OperationHandler<T : RemoteOperation, R : RemoteResponse> {

    protected abstract suspend fun executeActual(action: T): Result<R>

    suspend fun execute(data: RemoteOperation): Result<R> {
        @Suppress("UNCHECKED_CAST")
        val temp = data as? T ?: run {
            val throwable = Throwable("data has the wrong type")
            Log.e(TAG, "[IOperationHandler], execute: ", throwable)

            return Result.failure(throwable)
        }
        return executeActual(temp)
    }

    companion object {
        const val TAG = "IOperationHandler"
    }
}