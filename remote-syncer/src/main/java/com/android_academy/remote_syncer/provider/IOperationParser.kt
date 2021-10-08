package com.android_academy.remote_syncer.provider

import android.util.Log
import com.android_academy.remote_syncer.operations.RemoteOperation


abstract class IOperationParser<T : RemoteOperation> {

    protected abstract fun toJsonActual(operation: T): String?

    abstract fun fromJson(json: String): T?

    fun toJson(operation: RemoteOperation): String? {
        @Suppress("UNCHECKED_CAST")
        val specificOperation = operation as? T ?: run {
            Log.e(TAG, "toJson: ", Throwable("operation has the wrong type"))
            return null
        }
        return toJsonActual(specificOperation)
    }

    companion object {
        const val TAG = "IOperationParser"
    }
}