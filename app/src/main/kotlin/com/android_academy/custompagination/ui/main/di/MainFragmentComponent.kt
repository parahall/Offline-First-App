package com.android_academy.custompagination.ui.main.di

import com.android_academy.custompagination.di.FragmentScope
import com.android_academy.custompagination.ui.main.MainFragment
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