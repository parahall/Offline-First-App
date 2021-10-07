package com.android_academy.custompagination.remote.provider

import android.util.Log
import com.android_academy.custompagination.remote.models.RemoteResponse
import com.android_academy.custompagination.remote.operations.RemoteOperation

abstract class ISuccessHandler<T : RemoteOperation, R : RemoteResponse> {

    protected abstract suspend fun executeOnSuccessActual(action: T, response: R)

    @Suppress("UNCHECKED_CAST")
    suspend fun executeOnSuccess(data: RemoteOperation, response: RemoteResponse) {
        val tempData = data as? T ?: run {
            Log.d(TAG, "executeOnSuccess: ", Throwable("data has the wrong time"))
            return
        }
        val tempResponse = response as? R ?: run {
            Log.d(TAG, "executeOnSuccess: ", Throwable("response has the wrong type"))
            return
        }
        return executeOnSuccessActual(tempData, tempResponse)
    }

    companion object {
        const val TAG = "ISuccessHandler"
    }
}

class EmptySuccessHandler<T : RemoteOperation, R : RemoteResponse> : ISuccessHandler<T, R>() {
    override suspend fun executeOnSuccessActual(action: T, response: R) {}
}