package com.android_academy.custompagination.di

import android.app.Application
import android.content.Context
import com.android_academy.di_core.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
class AppModule(private val application: Application) {

    @Provides
    @ApplicationScope
    fun provideContext(): Context {
        return application.applicationContext
    }

}