package com.android_academy.custompagination.remote.models

import java.util.*

interface RemoteOperation {
    val identifier: RemoteOperationType
    val remoteCurrentData: RemoteCurrentData?
}

interface RemoteOperationType {
    val id: Int
    fun getName(): String
}

interface RemoteCurrentData

enum class StarWarsOperationType(override val id: Int): RemoteOperationType {
    CHANGE_FAVORITE_PERSON_STATUS(1)
    ;

    override fun getName() = name.lowercase(Locale.ROOT)

    companion object {
        fun fromId(id: Int?): StarWarsOperationType? {
            return values().find { it.id == id }
        }
    }
}

data class FavoriteStatusRemoteOperation(
    val personId: Int,
    val isFavor: Boolean
): RemoteOperation {
    override val identifier = StarWarsOperationType.CHANGE_FAVORITE_PERSON_STATUS
    override val remoteCurrentData: RemoteCurrentData? = null
}


