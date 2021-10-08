package com.android_academy.remote_syncer.operations.toggle_favorite_person

import com.android_academy.remote_syncer.provider.IOperationHandler

class ToggleFavoritePersonHandler :
    IOperationHandler<FavoriteStatusRemoteOperation, ToggleFavoritePersonResponse>() {
    override suspend fun executeActual(action: FavoriteStatusRemoteOperation): Result<ToggleFavoritePersonResponse> {
        TODO("Not yet implemented")
    }
}