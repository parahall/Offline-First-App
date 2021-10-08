package com.android_academy.remote_syncer.models

import com.android_academy.remote_syncer.operations.RemoteOperationType
import com.android_academy.remote_syncer.operations.StarWarsOperationType
import com.android_academy.storage.entities.PersistedRemoteDataEntity
import java.util.Date


interface RemoteCurrentData

interface RemoteResponse

data class RemoteData(
    val id: RemoteDataId,
    val timestamp: Date,
    val type: RemoteOperationType,
    val syncStatus: SyncStatus,
    val data: String,
    val metadata: Map<String, String>
)

fun RemoteData.toPersistedRemoteDataEntity(): PersistedRemoteDataEntity {
    return PersistedRemoteDataEntity(
        timestamp = timestamp,
        typeId = type.id,
        syncStatus = syncStatus.name,
        data = data,
        metadata = metadata,
    ).apply {
        id = this@toPersistedRemoteDataEntity.id
    }
}

fun PersistedRemoteDataEntity.toRemoteData(): RemoteData {
    val type = StarWarsOperationType.fromId(typeId) ?: run {
        throw Throwable("Invalid stored data. Not existing type for typeId: $typeId")
    }
    return RemoteData(
        id = id,
        timestamp = timestamp,
        type = type,
        syncStatus = SyncStatus.fromName(syncStatus) ?: SyncStatus.NEW,
        data = data,
        metadata = metadata
    )
}

typealias RemoteDataId = Long

enum class SyncStatus {
    NEW, PENDING, PENDING_RETRY, EXECUTING;

    companion object {
        fun fromName(name: String): SyncStatus? {
            return values().find { it.name == name }
        }
    }

}

const val NEW_ID = 0L