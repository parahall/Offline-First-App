package com.android_academy.storage.di

import android.content.Context
import androidx.room.Room
import com.android_academy.di_core.ApplicationScope
import com.android_academy.storage.StarWarsDb
import com.android_academy.storage.StorageSource
import com.android_academy.storage.StorageSourceImpl
import com.android_academy.storage.entities.RemoteSyncDataDao
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

    @Provides
    fun provideRemoteSyncDao(db: StarWarsDb): RemoteSyncDataDao {
        return db.remoteSyncDataDao()
    }
}