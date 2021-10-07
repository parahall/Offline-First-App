package com.android_academy.custompagination.remote.di

import com.android_academy.custompagination.remote.RemoteOperationMapper
import com.android_academy.custompagination.remote.RemoteOperationMapperImpl
import com.android_academy.custompagination.remote.RemoteOperationProvider
import com.android_academy.custompagination.remote.RemotePersistenceSource
import com.android_academy.custompagination.remote.RemotePersistenceSourceImpl
import com.android_academy.custompagination.remote.RemoteService
import com.android_academy.custompagination.remote.RemoteServiceImpl
import com.android_academy.custompagination.remote.operations.RemoteOperationType
import com.android_academy.custompagination.remote.operations.StarWarsOperationType
import com.android_academy.custompagination.remote.operations.toggle_favorite_person.FavoriteStatusRemoteOperation
import com.android_academy.custompagination.remote.operations.toggle_favorite_person.ToggleFavoritePersonFailureHandler
import com.android_academy.custompagination.remote.operations.toggle_favorite_person.ToggleFavoritePersonHandler
import com.android_academy.custompagination.remote.operations.toggle_favorite_person.ToggleFavoritePersonParser
import com.android_academy.custompagination.remote.operations.toggle_favorite_person.ToggleFavoritePersonProvider
import com.android_academy.custompagination.remote.operations.toggle_favorite_person.ToggleFavoritePersonResponse
import com.android_academy.custompagination.remote.provider.EmptySuccessHandler
import com.android_academy.custompagination.remote.provider.IFailureHandler
import com.android_academy.custompagination.remote.provider.IOperationHandler
import com.android_academy.custompagination.remote.provider.IOperationParser
import com.android_academy.custompagination.remote.provider.ISuccessHandler
import com.squareup.moshi.Moshi
import dagger.MapKey
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@MapKey
annotation class StarWarsOperationTypeKey(val value: StarWarsOperationType)

@Module
class RemoteOperationsModule {

    @Provides
    fun providePersistenceSource(): RemotePersistenceSource {
        return RemotePersistenceSourceImpl()
    }

    @Provides
    fun provideRemoteService(
        remoteOperationMapper: RemoteOperationMapper,
        persistenceSource: RemotePersistenceSource
    ): RemoteService =
        RemoteServiceImpl(
            remoteOperationMapper = remoteOperationMapper,
            persistenceSource = persistenceSource
        )

    @Provides
    fun provideRemoteOperationMapper(
        map: Map<@JvmSuppressWildcards StarWarsOperationType, @JvmSuppressWildcards RemoteOperationProvider<*, *>>
    ): RemoteOperationMapper {
        return RemoteOperationMapperImpl(map)
    }

    @Provides
    @IntoMap
    @StarWarsOperationTypeKey(StarWarsOperationType.CHANGE_FAVORITE_PERSON_STATUS)
    fun provideChangeFavoritePersonProvider(
        parser: IOperationParser<FavoriteStatusRemoteOperation>,
        operationHandler: IOperationHandler<FavoriteStatusRemoteOperation, ToggleFavoritePersonResponse>,
        failureHandler: IFailureHandler<FavoriteStatusRemoteOperation>,
        successHandler: ISuccessHandler<FavoriteStatusRemoteOperation, ToggleFavoritePersonResponse>
    ): RemoteOperationProvider<*, *> {
        return ToggleFavoritePersonProvider(
            parser,
            operationHandler,
            failureHandler,
            successHandler
        )
    }

    @Provides
    fun provideToggleFavoritePersonParser(moshi: Moshi): IOperationParser<FavoriteStatusRemoteOperation> {
        return ToggleFavoritePersonParser(moshi)
    }

    @Provides
    fun provideToggleFavoritePersonHandler(): IOperationHandler<FavoriteStatusRemoteOperation, ToggleFavoritePersonResponse> {
        return ToggleFavoritePersonHandler()
    }

    @Provides
    fun provideToggleFavoritePersonSuccessHandler(): ISuccessHandler<FavoriteStatusRemoteOperation, ToggleFavoritePersonResponse> {
        return EmptySuccessHandler()
    }


    @Provides
    fun provideToggleFavoritePersonFailureHandler(): IFailureHandler<FavoriteStatusRemoteOperation> {
        return ToggleFavoritePersonFailureHandler()
    }
}