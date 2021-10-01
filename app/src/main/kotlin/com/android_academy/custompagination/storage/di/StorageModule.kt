package com.android_academy.custompagination.storage.di

import android.content.Context
import androidx.room.Room
import com.android_academy.custompagination.di.ApplicationScope
import com.android_academy.custompagination.storage.StarWarsDb
import com.android_academy.custompagination.storage.StorageSource
import com.android_academy.custompagination.storage.StorageSourceImpl
import dagger.Module
import dagger.Provides

@Module
object StorageModule {

    @Provides
    @ApplicationScope
    fun provideDb(context: Context): StarWarsDb {
        return Room.databaseBuilder(
            context,
            StarWarsDb::class.java, "star_wars_db"
        ).fallbackToDestructiveMigration().build()
    }


    @Provides
    fun provideStorageSource(db: StarWarsDb): StorageSource {
        return StorageSourceImpl(db)
    }
}