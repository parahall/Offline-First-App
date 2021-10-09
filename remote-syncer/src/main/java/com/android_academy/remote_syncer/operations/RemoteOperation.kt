package com.android_academy.remote_syncer.operations

interface RemoteOperation {
    val identifier: RemoteOperationType
}

interface RemoteOperationType {
    val id: Int
}


enum class StarWarsOperationType(override val id: Int) : RemoteOperationType {
    CHANGE_FAVORITE_PERSON_STATUS(1)
    ;

    companion object {
        fun fromId(id: Int?): StarWarsOperationType? {
            return values().find { it.id == id }
        }
    }
}

