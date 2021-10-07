package com.android_academy.custompagination.remote

import com.android_academy.custompagination.remote.models.RemoteData

interface RemotePersistenceSource {
    fun store(remoteData: RemoteData)

}

class RemotePersistenceSourceImpl : RemotePersistenceSource {
    override fun store(remoteData: RemoteData) {
        TODO("Not yet implemented")
    }

}