package com.android_academy.custompagination.remote.operations.toggle_favorite_person

import com.android_academy.custompagination.remote.models.RemoteCurrentData
import com.android_academy.custompagination.remote.models.RemoteResponse
import com.android_academy.custompagination.remote.operations.RemoteOperation
import com.android_academy.custompagination.remote.operations.StarWarsOperationType

data class FavoriteStatusRemoteOperation(
    val personId: Int,
    val isFavor: Boolean
) : RemoteOperation {
    override val identifier = StarWarsOperationType.CHANGE_FAVORITE_PERSON_STATUS
    override val remoteCurrentData: RemoteCurrentData? = null
}

object ToggleFavoritePersonResponse : RemoteResponse