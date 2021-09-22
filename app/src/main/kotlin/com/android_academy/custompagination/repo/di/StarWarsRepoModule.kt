package com.android_academy.custompagination.repo.di

import com.android_academy.custompagination.network.StarWarsApi
import com.android_academy.custompagination.repo.StarWarsRepo
import com.android_academy.custompagination.ui.main.StarWarsDb
import dagger.Module
import dagger.Provides

@Module
object StarWarsRepoModule {

    @Provides
    fun provideRepo(db: StarWarsDb, api: StarWarsApi): StarWarsRepo {
        return StarWarsRepo(db, api)
    }
}