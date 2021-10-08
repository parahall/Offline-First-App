package com.android_academy.remote_syncer.operations.toggle_favorite_person

import com.android_academy.remote_syncer.provider.IOperationParser
import com.squareup.moshi.Moshi

class ToggleFavoritePersonParser(private val moshi: Moshi) :
    IOperationParser<FavoriteStatusRemoteOperation>() {
    override fun toJsonActual(operation: FavoriteStatusRemoteOperation): String? {
        val adapter = moshi.adapter(FavoriteStatusRemoteOperation::class.java)
        return adapter.toJson(operation)
    }

    override fun fromJson(json: String): FavoriteStatusRemoteOperation? {
        val adapter = moshi.adapter(FavoriteStatusRemoteOperation::class.java)
        return adapter.fromJson(json)
    }
}