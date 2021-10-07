package com.android_academy.custompagination.remote.operations.toggle_favorite_person

import com.android_academy.custompagination.remote.provider.IOperationParser
import com.squareup.moshi.Moshi

class ToggleFavoritePersonParser(private val moshi: Moshi) : IOperationParser<FavoriteStatusRemoteOperation>() {
    override fun toJsonActual(operation: FavoriteStatusRemoteOperation): String? {
        TODO("Not yet implemented")
    }

    override fun fromJson(json: String): FavoriteStatusRemoteOperation? {
        TODO("Not yet implemented")
    }
}