package com.android_academy.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.android_academy.storage.entities.EnrichDao
import com.android_academy.storage.entities.FavoritePersonEntity
import com.android_academy.storage.entities.FavoritesDao
import com.android_academy.storage.entities.FilmDao
import com.android_academy.storage.entities.FilmEntity
import com.android_academy.storage.entities.PersistedRemoteDataEntity
import com.android_academy.storage.entities.PersonDao
import com.android_academy.storage.entities.PersonEntity
import com.android_academy.storage.entities.PersonFilmsCrossRef
import com.android_academy.storage.entities.PersonSpecieCrossRef
import com.android_academy.storage.entities.PersonStarshipCrossRef
import com.android_academy.storage.entities.PersonVehicleCrossRef
import com.android_academy.storage.entities.RemoteSyncDataDao
import com.android_academy.storage.entities.SpecieDao
import com.android_academy.storage.entities.SpecieEntity
import com.android_academy.storage.entities.StarshipDao
import com.android_academy.storage.entities.StarshipEntity
import com.android_academy.storage.entities.VehicleDao
import com.android_academy.storage.entities.VehicleEntity

@Database(
    entities = [
        PersonEntity::class,
        FilmEntity::class,
        VehicleEntity::class,
        StarshipEntity::class,
        SpecieEntity::class,
        PersonFilmsCrossRef::class,
        PersonSpecieCrossRef::class,
        PersonVehicleCrossRef::class,
        PersonStarshipCrossRef::class,
        FavoritePersonEntity::class,
        PersistedRemoteDataEntity::class,
    ],
    version = 5,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class StarWarsDb : RoomDatabase() {
    abstract fun personDao(): PersonDao
    abstract fun filmDao(): FilmDao
    abstract fun vehicleDao(): VehicleDao
    abstract fun starshipDao(): StarshipDao
    abstract fun specieDao(): SpecieDao
    abstract fun enrichedDao(): EnrichDao
    abstract fun favoritesDao(): FavoritesDao
    abstract fun remoteSyncDataDao(): RemoteSyncDataDao
}



