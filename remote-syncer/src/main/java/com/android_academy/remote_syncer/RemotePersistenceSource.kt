package com.android_academy.remote_syncer

import com.android_academy.remote_syncer.models.RemoteData
import com.android_academy.remote_syncer.models.RemoteDataId
import com.android_academy.remote_syncer.models.toPersistedRemoteDataEntity
import com.android_academy.remote_syncer.models.toRemoteData
import com.android_academy.storage.entities.RemoteSyncDataDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface RemotePersistenceSource {
    suspend fun store(remoteData: RemoteData)
    fun observeData(): Flow<List<RemoteData>>
    suspend fun isQueueIsEmpty(): Boolean
    suspend fun remove(remoteData: RemoteData)
    suspend fun update(remoteData: RemoteData)
    suspend fun getRemoteData(id: RemoteDataId): RemoteData?
}

class RemotePersistenceSourceImpl(private val dao: RemoteSyncDataDao) :
    RemotePersistenceSource {
    override suspend fun store(remoteData: RemoteData) {
        dao.insert(remoteData.toPersistedRemoteDataEntity())
    }

    override fun observeData(): Flow<List<RemoteData>> =
        dao.observeData().map { remoteData ->
            remoteData.map { it.toRemoteData() }
        }

    override suspend fun isQueueIsEmpty(): Boolean {
        return dao.getQueueSize() == 0
    }

    override suspend fun remove(remoteData: RemoteData) {
        remoteData.toPersistedRemoteDataEntity().let { remoteEntity ->
            dao.remove(remoteEntity)
        }
    }

    override suspend fun update(remoteData: RemoteData) {
        remoteData.toPersistedRemoteDataEntity().let { boardRemoteData ->
            dao.update(boardRemoteData)
        }
    }

    override suspend fun getRemoteData(id: RemoteDataId): RemoteData? {
        return dao.getRemoteData(id)?.toRemoteData()
    }

}


