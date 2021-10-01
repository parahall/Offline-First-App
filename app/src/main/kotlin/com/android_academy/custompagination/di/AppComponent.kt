package com.android_academy.custompagination.di

import com.android_academy.custompagination.network.StarWarsApi
import com.android_academy.custompagination.network.di.NetworkModule
import com.android_academy.custompagination.repo.StarWarsRepo
import com.android_academy.custompagination.repo.di.StarWarsRepoModule
import com.android_academy.custompagination.storage.StarWarsDb
import com.android_academy.custompagination.storage.di.StorageModule
import com.android_academy.custompagination.ui.main.di.MainFragmentComponent
import com.squareup.moshi.Moshi
import dagger.Component

@ApplicationScope
@Component(modules = [NetworkModule::class, StorageModule::class, AppModule::class, StarWarsRepoModule::class])
interface AppComponent {

    fun starWarsApi(): StarWarsApi
    fun starWarsDb(): StarWarsDb
    fun starWarsRepo(): StarWarsRepo
    fun moshi(): Moshi
    fun provideMainFragSubcomponent(): MainFragmentComponent.Factory
}