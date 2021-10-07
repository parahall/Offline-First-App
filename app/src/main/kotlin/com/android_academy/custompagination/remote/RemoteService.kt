package com.android_academy.custompagination.remote

import com.android_academy.custompagination.remote.models.RemoteOperation

interface RemoteService {
    suspend fun performRemoteOperation(
        operation: RemoteOperation,
        metadata: Map<String, String> = emptyMap()
    )
}


class RemoteServiceImpl : RemoteService {
    override suspend fun performRemoteOperation(
        operation: RemoteOperation,
        metadata: Map<String, String>
    ) {
        TODO("Not yet implemented")
    }
}