package com.android_academy.remote_syncer.operations.toggle_favorite_person

import com.android_academy.remote_syncer.provider.IOperationParser
import com.squareup.moshi.Moshi

class ToggleFavoritePersonParser(private val moshi: Moshi) :
    IOperationParser<FavoriteRemoteOperation>() {
    override fun toJsonActual(operation: FavoriteRemoteOperation): String? {
        val adapter = moshi.adapter(FavoriteRemoteOperation::class.java)
        return adapter.toJson(operation)
    }

    override fun fromJson(json: String): FavoriteRemoteOperation? {
        val adapter = moshi.adapter(FavoriteRemoteOperation::class.java)
        return adapter.fromJson(json)
    }
}