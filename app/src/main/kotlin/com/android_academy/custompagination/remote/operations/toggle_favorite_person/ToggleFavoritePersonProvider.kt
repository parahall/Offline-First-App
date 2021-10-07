package com.android_academy.custompagination.remote.operations.toggle_favorite_person

import com.android_academy.custompagination.remote.RemoteOperationProvider
import com.android_academy.custompagination.remote.provider.IFailureHandler
import com.android_academy.custompagination.remote.provider.IOperationHandler
import com.android_academy.custompagination.remote.provider.IOperationParser
import com.android_academy.custompagination.remote.provider.ISuccessHandler

class ToggleFavoritePersonProvider(
    private val parser: IOperationParser<FavoriteStatusRemoteOperation>,
    private val operationHandler: IOperationHandler<FavoriteStatusRemoteOperation, ToggleFavoritePersonResponse>,
    private val failureHandler: IFailureHandler<FavoriteStatusRemoteOperation>,
    private val successHandler: ISuccessHandler<FavoriteStatusRemoteOperation, ToggleFavoritePersonResponse>
) :
    RemoteOperationProvider<FavoriteStatusRemoteOperation, ToggleFavoritePersonResponse> {
    override fun provideParser() = parser

    override fun providerOperationHandler() = operationHandler

    override fun provideSuccessHandler() = successHandler

    override fun provideFailureHandler() = failureHandler
}