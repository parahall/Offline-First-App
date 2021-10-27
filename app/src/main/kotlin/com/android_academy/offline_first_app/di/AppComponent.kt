package com.android_academy.offline_first_app.di

import com.android_academy.offline_first_app.prefetch.PushMessagingService
import com.android_academy.offline_first_app.prefetch.StarWarsWorker
import com.android_academy.offline_first_app.repo.StarWarsRepo
import com.android_academy.offline_first_app.repo.di.StarWarsRepoModule
import com.android_academy.offline_first_app.ui.main.di.MainFragmentComponent
import com.android_academy.di_core.ApplicationScope
import com.android_academy.network.StarWarsApi
import com.android_academy.network.di.NetworkModule
import com.android_academy.remote_syncer.RemoteDataObserver
import com.android_academy.remote_syncer.SpecificRemoteWorker
import com.android_academy.remote_syncer.di.RemoteOperationsModule
import com.android_academy.storage.StarWarsDb
import com.android_academy.storage.di.StorageModule
import com.squareup.moshi.Moshi
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        NetworkModule::class,
        StorageModule::class,
        AppModule::class,
        StarWarsRepoModule::class,
        RemoteOperationsModule::class]
)
interface AppComponent {

    fun starWarsApi(): StarWarsApi
    fun starWarsDb(): StarWarsDb
    fun starWarsRepo(): StarWarsRepo
    fun remoteObserver() : RemoteDataObserver
    fun moshi(): Moshi
    fun provideMainFragSubcomponent(): MainFragmentComponent.Factory
    fun inject(starWarsWorker: StarWarsWorker)
    fun inject(starWarsWorker: PushMessagingService)
    fun inject(specificRemoteWorker: SpecificRemoteWorker)
}