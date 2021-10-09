package com.android_academy.remote_syncer.operations.toggle_favorite_person

import com.android_academy.remote_syncer.provider.RemoteOperationProvider
import com.android_academy.remote_syncer.provider.IFailureHandler
import com.android_academy.remote_syncer.provider.IOperationHandler
import com.android_academy.remote_syncer.provider.IOperationParser
import com.android_academy.remote_syncer.ISuccessHandler

class ToggleFavoritePersonProvider(
    private val parser: IOperationParser<FavoriteRemoteOperation>,
    private val operationHandler: IOperationHandler<FavoriteRemoteOperation, FavoritePersonResponse>,
    private val failureHandler: IFailureHandler<FavoriteRemoteOperation>,
    private val successHandler: ISuccessHandler<FavoriteRemoteOperation, FavoritePersonResponse>
) :
    RemoteOperationProvider<FavoriteRemoteOperation, FavoritePersonResponse> {
    override fun provideParser() = parser

    override fun providerOperationHandler() = operationHandler

    override fun provideSuccessHandler() = successHandler

    override fun provideFailureHandler() = failureHandler
}