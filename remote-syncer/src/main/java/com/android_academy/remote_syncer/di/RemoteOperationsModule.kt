package com.android_academy.remote_syncer.di

import com.android_academy.remote_syncer.EmptySuccessHandler
import com.android_academy.remote_syncer.ISuccessHandler
import com.android_academy.remote_syncer.RemotePersistenceSource
import com.android_academy.remote_syncer.RemotePersistenceSourceImpl
import com.android_academy.remote_syncer.RemoteService
import com.android_academy.remote_syncer.RemoteServiceImpl
import com.android_academy.remote_syncer.operations.StarWarsOperationType
import com.android_academy.remote_syncer.operations.toggle_favorite_person.FavoriteStatusRemoteOperation
import com.android_academy.remote_syncer.operations.toggle_favorite_person.ToggleFavoritePersonFailureHandler
import com.android_academy.remote_syncer.operations.toggle_favorite_person.ToggleFavoritePersonHandler
import com.android_academy.remote_syncer.operations.toggle_favorite_person.ToggleFavoritePersonParser
import com.android_academy.remote_syncer.operations.toggle_favorite_person.ToggleFavoritePersonProvider
import com.android_academy.remote_syncer.operations.toggle_favorite_person.ToggleFavoritePersonResponse
import com.android_academy.remote_syncer.provider.IFailureHandler
import com.android_academy.remote_syncer.provider.IOperationHandler
import com.android_academy.remote_syncer.provider.IOperationParser
import com.android_academy.remote_syncer.provider.RemoteOperationMapper
import com.android_academy.remote_syncer.provider.RemoteOperationMapperImpl
import com.android_academy.remote_syncer.provider.RemoteOperationProvider
import com.android_academy.storage.entities.RemoteSyncDataDao
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
    fun providePersistenceSource(remoteSyncDataDao: RemoteSyncDataDao): RemotePersistenceSource {
        return RemotePersistenceSourceImpl(remoteSyncDataDao = remoteSyncDataDao)
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