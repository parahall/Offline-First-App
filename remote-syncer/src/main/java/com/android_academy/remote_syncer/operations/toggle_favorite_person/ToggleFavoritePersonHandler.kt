package com.android_academy.remote_syncer.operations.toggle_favorite_person

import com.android_academy.network.StarWarsApi
import com.android_academy.network.safeResultApiCall
import com.android_academy.remote_syncer.provider.IOperationHandler

class ToggleFavoritePersonHandler(private val starWarsApi: StarWarsApi) :
    IOperationHandler<FavoriteRemoteOperation, FavoritePersonResponse>() {
    override suspend fun executeActual(action: FavoriteRemoteOperation): Result<FavoritePersonResponse> {
        val markPersonFavorite =
            safeResultApiCall { starWarsApi.markPersonFavorite(action.personId, action.isFavor) }
        return if (markPersonFavorite.isSuccess) {
            Result.success(FavoritePersonResponse)
        } else {
            Result.failure(
                markPersonFavorite.exceptionOrNull()
                    ?: Throwable("Network call markPersonFavorite failed")
            )
        }
    }
}