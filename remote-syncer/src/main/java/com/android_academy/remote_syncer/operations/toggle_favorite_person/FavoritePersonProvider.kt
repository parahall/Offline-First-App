package com.android_academy.remote_syncer.operations.toggle_favorite_person

import com.android_academy.remote_syncer.provider.RemoteOperationProvider
import com.android_academy.remote_syncer.provider.FailureHandler
import com.android_academy.remote_syncer.provider.OperationExecutor
import com.android_academy.remote_syncer.provider.OperationParser
import com.android_academy.remote_syncer.provider.SuccessHandler

class FavoritePersonProvider(
    private val parser: OperationParser<FavoriteRemoteOperation>,
    private val operationHandler: OperationExecutor<FavoriteRemoteOperation, FavoritePersonResponse>,
    private val failureHandler: FailureHandler<FavoriteRemoteOperation>,
    private val successHandler: SuccessHandler<FavoriteRemoteOperation, FavoritePersonResponse>
) :
    RemoteOperationProvider<FavoriteRemoteOperation, FavoritePersonResponse> {
    override fun provideParser() = parser

    override fun providerOperationHandler() = operationHandler

    override fun provideSuccessHandler() = successHandler

    override fun provideFailureHandler() = failureHandler
}