package com.android_academy.custompagination.ui.main.di

import androidx.lifecycle.ViewModelProvider
import com.android_academy.custompagination.repo.StarWarsRepo
import com.android_academy.custompagination.ui.main.MainFragment
import com.android_academy.custompagination.ui.main.MainFragmentViewModelFactory
import com.android_academy.custompagination.ui.main.MainViewModel
import dagger.Module
import dagger.Provides

@Module
object MainFragmentModule {

    @Provides
    fun provideViewModelFactory(starWarsRepo: StarWarsRepo): MainFragmentViewModelFactory {
        return MainFragmentViewModelFactory(starWarsRepo)
    }

    @Provides
    fun provideViewModel(
        factory: MainFragmentViewModelFactory,
        fragment: MainFragment
    ): MainViewModel {
        return ViewModelProvider(fragment, factory).get(MainViewModel::class.java)
    }
}