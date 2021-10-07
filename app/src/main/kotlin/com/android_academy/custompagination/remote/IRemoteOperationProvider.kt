package com.android_academy.custompagination.remote

import com.android_academy.custompagination.remote.models.RemoteResponse
import com.android_academy.custompagination.remote.operations.RemoteOperation
import com.android_academy.custompagination.remote.operations.RemoteOperationType
import com.android_academy.custompagination.remote.operations.StarWarsOperationType
import com.android_academy.custompagination.remote.provider.IFailureHandler
import com.android_academy.custompagination.remote.provider.IOperationHandler
import com.android_academy.custompagination.remote.provider.IOperationParser
import com.android_academy.custompagination.remote.provider.ISuccessHandler

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