package com.android_academy.remote_syncer.provider

import android.util.Log
import com.android_academy.remote_syncer.operations.RemoteOperation

abstract class IFailureHandler<T : RemoteOperation> {

    protected abstract suspend fun executeOnFailureActual(data: T, throwable: Throwable)

    suspend fun executeOnFailure(data: RemoteOperation, throwable: Throwable) {
        @Suppress("UNCHECKED_CAST")
        val temp = data as? T ?: run {
            Log.d(TAG, "executeOnFailure: ", Throwable("data has the wrong type"))
            return
        }
        return executeOnFailureActual(temp, throwable)
    }

    companion object {
        const val TAG = "IFailureHandler"
    }
}