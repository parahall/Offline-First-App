package com.android_academy.custompagination.remote.operations.toggle_favorite_person

import com.android_academy.custompagination.remote.provider.IFailureHandler

class ToggleFavoritePersonFailureHandler : IFailureHandler<FavoriteStatusRemoteOperation>() {
    override suspend fun executeOnFailureActual(
        data: FavoriteStatusRemoteOperation,
        throwable: Throwable
    ) {
        TODO("Not yet implemented")
    }
}