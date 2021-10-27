package com.android_academy.offline_first_app.ui.main.di

import androidx.lifecycle.ViewModelProvider
import com.android_academy.offline_first_app.repo.StarWarsRepo
import com.android_academy.offline_first_app.ui.main.MainFragment
import com.android_academy.offline_first_app.ui.main.MainFragmentViewModelFactory
import com.android_academy.offline_first_app.ui.main.MainViewModel
import dagger.Module
import dagger.Provides

@Module
object MainFragmentModule {

    @Provides
    fun provideViewModelFactory(
        starWarsRepo: StarWarsRepo,
    ): MainFragmentViewModelFactory {
        return MainFragmentViewModelFactory(starWarsRepo)
    }

    @Provides
    fun provideViewModel(
        factory: MainFragmentViewModelFactory,
        fragment: MainFragment,
    ): MainViewModel {
        return ViewModelProvider(fragment, factory).get(MainViewModel::class.java)
    }
}