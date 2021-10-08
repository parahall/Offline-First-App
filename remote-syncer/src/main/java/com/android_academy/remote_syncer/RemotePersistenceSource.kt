package com.android_academy.remote_syncer

import com.android_academy.remote_syncer.models.RemoteData
import com.android_academy.remote_syncer.models.toPersistedRemoteDataEntity
import com.android_academy.storage.entities.RemoteSyncDataDao

interface RemotePersistenceSource {
    suspend fun store(remoteData: RemoteData)

}

class RemotePersistenceSourceImpl(private val remoteSyncDataDao: RemoteSyncDataDao) :
    RemotePersistenceSource {
    override suspend fun store(remoteData: RemoteData) {
        remoteSyncDataDao.insert(remoteData.toPersistedRemoteDataEntity())
    }

}