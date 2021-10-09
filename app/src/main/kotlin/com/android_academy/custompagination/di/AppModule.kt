package com.android_academy.custompagination.di

import android.app.Application
import android.content.Context
import androidx.work.Configuration
import androidx.work.WorkManager
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

    @Provides
    @ApplicationScope
    fun provideWorkManager(context: Context): WorkManager {
        return WorkManager.getInstance(context)
    }

}