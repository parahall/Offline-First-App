package com.android_academy.offline_first_app.repo.di

import com.android_academy.remote_syncer.RemoteService
import com.android_academy.offline_first_app.repo.StarWarsRepo
import com.android_academy.offline_first_app.repo.StarWarsRepoImpl
import com.android_academy.network.NetworkSource
import com.android_academy.storage.StorageSource
import dagger.Module
import dagger.Provides


@Module
object StarWarsRepoModule {

    @Provides
    fun provideRepo(
        storageSource: StorageSource,
        networkSource: NetworkSource,
        remoteService: RemoteService
    ): StarWarsRepo {
        return StarWarsRepoImpl(storageSource, networkSource, remoteService)
    }
}