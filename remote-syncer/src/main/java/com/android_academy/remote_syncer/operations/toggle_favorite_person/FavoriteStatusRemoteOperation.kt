package com.android_academy.remote_syncer.operations.toggle_favorite_person

import com.android_academy.remote_syncer.models.RemoteCurrentData
import com.android_academy.remote_syncer.models.RemoteResponse
import com.android_academy.remote_syncer.operations.RemoteOperation
import com.android_academy.remote_syncer.operations.StarWarsOperationType
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FavoriteStatusRemoteOperation(
    val personId: Int,
    val isFavor: Boolean
) : RemoteOperation {
    override val identifier = StarWarsOperationType.CHANGE_FAVORITE_PERSON_STATUS
    override val remoteCurrentData: RemoteCurrentData? = null
}

object ToggleFavoritePersonResponse : RemoteResponse