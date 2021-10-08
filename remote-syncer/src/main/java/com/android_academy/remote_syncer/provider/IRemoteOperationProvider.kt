package com.android_academy.remote_syncer.provider

import com.android_academy.remote_syncer.ISuccessHandler
import com.android_academy.remote_syncer.models.RemoteResponse
import com.android_academy.remote_syncer.operations.RemoteOperation
import com.android_academy.remote_syncer.operations.RemoteOperationType
import com.android_academy.remote_syncer.operations.StarWarsOperationType

interface RemoteOperationMapper {
    operator fun get(type: RemoteOperationType): RemoteOperationProvider<*, *>?
}

class RemoteOperationMapperImpl(
    private val map: Map<StarWarsOperationType, RemoteOperationProvider<*, *>>
) : RemoteOperationMapper {

    override fun get(type: RemoteOperationType): RemoteOperationProvider<*, *>? {
        return map[type]
    }
}


interface RemoteOperationProvider<T : RemoteOperation, R : RemoteResponse> {
    fun provideParser(): IOperationParser<T>
    fun providerOperationHandler(): IOperationHandler<T, R>
    fun provideSuccessHandler(): ISuccessHandler<T, R>
    fun provideFailureHandler(): IFailureHandler<T>
}