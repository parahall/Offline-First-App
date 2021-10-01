package com.android_academy.custompagination.repo.di

import com.android_academy.custompagination.network.NetworkSource
import com.android_academy.custompagination.repo.StarWarsRepo
import com.android_academy.custompagination.storage.StorageSource
import dagger.Module
import dagger.Provides

@Module
object StarWarsRepoModule {

    @Provides
    fun provideRepo(
        storageSource: StorageSource,
        networkSource: NetworkSource,
    ): StarWarsRepo {
        return StarWarsRepo(storageSource, networkSource)
    }
}