package com.android_academy.remote_syncer.operations.toggle_favorite_person

import android.util.Log
import com.android_academy.remote_syncer.provider.IFailureHandler
import com.android_academy.storage.StorageSource

class ToggleFavoritePersonFailureHandler(private val storageSource: StorageSource) :
    IFailureHandler<FavoriteRemoteOperation>() {
    override suspend fun executeOnFailureActual(
        data: FavoriteRemoteOperation,
        throwable: Throwable?
    ) {
        Log.e(
            TAG,
            "executeOnFailureActual: Fail to execute toggle favor action for person ${data.personId}",
            throwable
        )
        storageSource.updatePerson(data.personId, !data.isFavor)
    }
}