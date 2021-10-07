package com.android_academy.custompagination.remote.operations

import com.android_academy.custompagination.remote.models.RemoteCurrentData
import java.util.Locale

interface RemoteOperation {
    val identifier: RemoteOperationType
    val remoteCurrentData: RemoteCurrentData?
}

interface RemoteOperationType {
    val id: Int
    fun getName(): String
}


enum class StarWarsOperationType(override val id: Int) : RemoteOperationType {
    CHANGE_FAVORITE_PERSON_STATUS(1)
    ;

    override fun getName() = name.lowercase(Locale.ROOT)

    companion object {
        fun fromId(id: Int?): StarWarsOperationType? {
            return values().find { it.id == id }
        }
    }
}

