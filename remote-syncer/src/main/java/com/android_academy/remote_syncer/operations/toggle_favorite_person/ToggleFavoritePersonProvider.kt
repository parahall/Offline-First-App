package com.android_academy.remote_syncer.operations.toggle_favorite_person

import com.android_academy.remote_syncer.provider.RemoteOperationProvider
import com.android_academy.remote_syncer.provider.FailureHandler
import com.android_academy.remote_syncer.provider.OperationHandler
import com.android_academy.remote_syncer.provider.OperationParser
import com.android_academy.remote_syncer.provider.SuccessHandler

class ToggleFavoritePersonProvider(
    private val parser: OperationParser<FavoriteRemoteOperation>,
    private val operationHandler: OperationHandler<FavoriteRemoteOperation, FavoritePersonResponse>,
    private val failureHandler: FailureHandler<FavoriteRemoteOperation>,
    private val successHandler: SuccessHandler<FavoriteRemoteOperation, FavoritePersonResponse>
) :
    RemoteOperationProvider<FavoriteRemoteOperation, FavoritePersonResponse> {
    override fun provideParser() = parser

    override fun providerOperationHandler() = operationHandler

    override fun provideSuccessHandler() = successHandler

    override fun provideFailureHandler() = failureHandler
}