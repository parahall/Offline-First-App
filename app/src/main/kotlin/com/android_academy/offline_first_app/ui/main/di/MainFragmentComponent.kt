package com.android_academy.offline_first_app.ui.main.di

import com.android_academy.offline_first_app.ui.main.MainFragment
import com.android_academy.di_core.FragmentScope
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(modules = [MainFragmentModule::class])
@FragmentScope
interface MainFragmentComponent {
    fun inject(fragment: MainFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance mainFragment: MainFragment): MainFragmentComponent
    }
}