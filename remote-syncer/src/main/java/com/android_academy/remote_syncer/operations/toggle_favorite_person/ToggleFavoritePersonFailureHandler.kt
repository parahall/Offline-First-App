package com.android_academy.remote_syncer.operations.toggle_favorite_person

import com.android_academy.remote_syncer.provider.IFailureHandler

class ToggleFavoritePersonFailureHandler : IFailureHandler<FavoriteStatusRemoteOperation>() {
    override suspend fun executeOnFailureActual(
        data: FavoriteStatusRemoteOperation,
        throwable: Throwable
    ) {
        TODO("Not yet implemented")
    }
}