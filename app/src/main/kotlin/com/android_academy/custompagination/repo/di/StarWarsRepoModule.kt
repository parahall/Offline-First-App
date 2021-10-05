package com.android_academy.custompagination.repo.di

import com.android_academy.network.NetworkSource
import com.android_academy.custompagination.repo.StarWarsRepo
import com.android_academy.custompagination.repo.StarWarsRepoImpl
import com.android_academy.storage.StorageSource
import dagger.Module
import dagger.Provides

@Module
object StarWarsRepoModule {

    @Provides
    fun provideRepo(
        storageSource: StorageSource,
        networkSource: NetworkSource,
    ): StarWarsRepo {
        return StarWarsRepoImpl(storageSource, networkSource)
    }
}