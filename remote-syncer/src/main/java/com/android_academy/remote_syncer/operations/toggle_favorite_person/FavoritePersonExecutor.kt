package com.android_academy.remote_syncer.operations.toggle_favorite_person

import com.android_academy.network.StarWarsApi
import com.android_academy.network.safeResultApiCall
import com.android_academy.remote_syncer.provider.OperationExecutor

class FavoritePersonExecutor(private val starWarsApi: StarWarsApi) :
    OperationExecutor<FavoriteRemoteOperation, FavoritePersonResponse>() {
    override suspend fun executeActual(operation: FavoriteRemoteOperation): Result<FavoritePersonResponse> {
        val markPersonFavorite =
            safeResultApiCall { starWarsApi.markPersonFavorite(operation.personId, operation.isFavor) }
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