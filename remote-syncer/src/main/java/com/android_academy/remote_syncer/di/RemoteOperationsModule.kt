package com.android_academy.remote_syncer.di

import androidx.work.WorkManager
import com.android_academy.network.StarWarsApi
import com.android_academy.remote_syncer.provider.EmptySuccessHandler
import com.android_academy.remote_syncer.provider.SuccessHandler
import com.android_academy.remote_syncer.RemoteDataObserver
import com.android_academy.remote_syncer.RemoteDataObserverImpl
import com.android_academy.remote_syncer.RemotePersistenceSource
import com.android_academy.remote_syncer.RemotePersistenceSourceImpl
import com.android_academy.remote_syncer.RemoteScheduler
import com.android_academy.remote_syncer.RemoteSchedulerImpl
import com.android_academy.remote_syncer.RemoteService
import com.android_academy.remote_syncer.RemoteServiceImpl
import com.android_academy.remote_syncer.SpecificRemoteWorker
import com.android_academy.remote_syncer.SyncService
import com.android_academy.remote_syncer.SyncServiceImpl
import com.android_academy.remote_syncer.operations.StarWarsOperationType
import com.android_academy.remote_syncer.operations.toggle_favorite_person.FavoritePersonResponse
import com.android_academy.remote_syncer.operations.toggle_favorite_person.FavoriteRemoteOperation
import com.android_academy.remote_syncer.operations.toggle_favorite_person.FavoritePersonFailureHandler
import com.android_academy.remote_syncer.operations.toggle_favorite_person.FavoritePersonExecutor
import com.android_academy.remote_syncer.operations.toggle_favorite_person.ToggleFavoritePersonParser
import com.android_academy.remote_syncer.operations.toggle_favorite_person.FavoritePersonProvider
import com.android_academy.remote_syncer.provider.FailureHandler
import com.android_academy.remote_syncer.provider.OperationExecutor
import com.android_academy.remote_syncer.provider.OperationParser
import com.android_academy.remote_syncer.provider.RemoteOperationMapper
import com.android_academy.remote_syncer.provider.RemoteOperationMapperImpl
import com.android_academy.remote_syncer.provider.RemoteOperationProvider
import com.android_academy.storage.StorageSource
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
    fun provideRemoteObserver(
        persistenceSource: RemotePersistenceSource,
        mapper: RemoteOperationMapper,
                  remoteScheduler: RemoteScheduler,
    ): RemoteDataObserver {
        return RemoteDataObserverImpl(
            persistenceSource = persistenceSource,
            mapper = mapper,
            remoteScheduler = remoteScheduler
        )
    }

    @Provides
    fun provideRemoteSchedule(
        workManager: WorkManager,
        persistentSource: RemotePersistenceSource
    ): RemoteScheduler {
        return RemoteSchedulerImpl(
            workManager = workManager,
            workerClass = SpecificRemoteWorker::class.java,
            workName = "REMOTE_QUEUE_WORKER",
            persistentSource = persistentSource
        )
    }

    @Provides
    fun provideSyncService(
        persistenceSource: RemotePersistenceSource,
        mapper: RemoteOperationMapper
    ): SyncService {
        return SyncServiceImpl(mapper = mapper, persistentSource = persistenceSource)
    }

    @Provides
    fun providePersistenceSource(remoteSyncDataDao: RemoteSyncDataDao): RemotePersistenceSource {
        return RemotePersistenceSourceImpl(dao = remoteSyncDataDao)
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
        parser: OperationParser<FavoriteRemoteOperation>,
        operationHandler: OperationExecutor<FavoriteRemoteOperation, FavoritePersonResponse>,
        failureHandler: FailureHandler<FavoriteRemoteOperation>,
        successHandler: SuccessHandler<FavoriteRemoteOperation, FavoritePersonResponse>
    ): RemoteOperationProvider<*, *> {
        return FavoritePersonProvider(
            parser,
            operationHandler,
            failureHandler,
            successHandler
        )
    }

    @Provides
    fun provideToggleFavoritePersonParser(moshi: Moshi): OperationParser<FavoriteRemoteOperation> {
        return ToggleFavoritePersonParser(moshi)
    }

    @Provides
    fun provideToggleFavoritePersonHandler(starWarsApi: StarWarsApi): OperationExecutor<FavoriteRemoteOperation, FavoritePersonResponse> {
        return FavoritePersonExecutor(starWarsApi)
    }

    @Provides
    fun provideToggleFavoritePersonSuccessHandler(): SuccessHandler<FavoriteRemoteOperation, FavoritePersonResponse> {
        return EmptySuccessHandler()
    }


    @Provides
    fun provideToggleFavoritePersonFailureHandler(storageSource: StorageSource): FailureHandler<FavoriteRemoteOperation> {
        return FavoritePersonFailureHandler(storageSource)
    }
}