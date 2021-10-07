package com.android_academy.custompagination.remote.models

import com.android_academy.custompagination.remote.operations.RemoteOperationType
import java.util.*


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

typealias RemoteDataId = Long

enum class SyncStatus {
    NEW, PENDING, PENDING_RETRY, EXECUTING
}

const val NEW_ID = 0L