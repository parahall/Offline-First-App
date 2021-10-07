package com.android_academy.custompagination.remote

import com.android_academy.custompagination.remote.models.NEW_ID
import com.android_academy.custompagination.remote.models.RemoteData
import com.android_academy.custompagination.remote.models.SyncStatus
import com.android_academy.custompagination.remote.operations.RemoteOperation
import java.util.Date

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
        val remoteOperationProvider = remoteOperationMapper[operation.identifier] ?: return
        val jsonData = remoteOperationProvider.provideParser().toJson(operation) ?: return
        val remoteData = RemoteData(
            id = NEW_ID,
            timestamp = Date(),
            type = operation.identifier,
            syncStatus = SyncStatus.NEW,
            data = jsonData,
            metadata = metadata
        )

        persistenceSource.store(remoteData)

    }
}



