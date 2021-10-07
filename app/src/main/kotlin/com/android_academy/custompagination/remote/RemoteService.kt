package com.android_academy.custompagination.remote

import com.android_academy.custompagination.remote.operations.RemoteOperation

interface RemoteService {
    suspend fun performRemoteOperation(
        operation: RemoteOperation,
        metadata: Map<String, String> = emptyMap()
    )
}


class RemoteServiceImpl(
    val remoteOperationMapper: RemoteOperationMapper,
    val persistenceSource: RemotePersistenceSource
) : RemoteService {
    override suspend fun performRemoteOperation(
        operation: RemoteOperation,
        metadata: Map<String, String>
    ) {

    }
}



