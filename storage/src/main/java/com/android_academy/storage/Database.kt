package com.android_academy.storage

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.Transaction
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.android_academy.storage.entities.EnrichedPersonEntity
import com.android_academy.storage.entities.FavoritePersonEntity
import com.android_academy.storage.entities.FilmEntity
import com.android_academy.storage.entities.PersonEntity
import com.android_academy.storage.entities.PersonFilmsCrossRef
import com.android_academy.storage.entities.PersonSpecieCrossRef
import com.android_academy.storage.entities.PersonStarshipCrossRef
import com.android_academy.storage.entities.PersonVehicleCrossRef
import com.android_academy.storage.entities.SpecieEntity
import com.android_academy.storage.entities.StarshipEntity
import com.android_academy.storage.entities.VehicleEntity
import kotlinx.coroutines.flow.Flow

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
    ],
    version = 3,
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
}

@Dao
interface FavoritesDao {
    @Transaction
    fun toggleFavorites(personId: Int) : Boolean {
        val isFavor = getFavoriteStatus(personId) ?: false
        insert(FavoritePersonEntity(personId, !isFavor))
        return !isFavor
    }

    @Query("SELECT is_favorite FROM favorite_people_table WHERE person_id = :personId")
    fun getFavoriteStatus(personId: Int): Boolean?

    @Insert(onConflict = REPLACE)
    fun insert(entity: FavoritePersonEntity)
}

@Dao
interface EnrichDao {
    @Transaction
    @Query("SELECT * FROM people_table")
    fun getAll(): Flow<List<EnrichedPersonEntity>>

    @Insert(onConflict = IGNORE)
    fun insertAll(vararg crossRef: PersonFilmsCrossRef)

    @Insert(onConflict = IGNORE)
    fun insertAll(vararg crossRef: PersonSpecieCrossRef)

    @Insert(onConflict = IGNORE)

    fun insertAll(vararg crossRef: PersonVehicleCrossRef)

    @Insert(onConflict = IGNORE)
    fun insertAll(vararg crossRef: PersonStarshipCrossRef)
}

@Dao
interface PersonDao {
    @Query("SELECT * FROM people_table")
    fun getAll(): Flow<List<PersonEntity>>

    @Insert(onConflict = REPLACE)
    fun insertAll(vararg people: PersonEntity)

    @Query("SELECT * FROM people_table")
    fun getAllSync(): List<PersonEntity>
}

@Dao
interface FilmDao {
    @Query("SELECT * FROM films_table")
    fun getAll(): Flow<List<FilmEntity>>

    @Insert(onConflict = REPLACE)
    fun insertAll(vararg people: FilmEntity)
}

@Dao
interface VehicleDao {
    @Query("SELECT * FROM vehicle_table")
    fun getAll(): Flow<List<VehicleEntity>>

    @Insert(onConflict = REPLACE)
    fun insertAll(vararg people: VehicleEntity)
}

@Dao
interface StarshipDao {
    @Query("SELECT * FROM starship_table")
    fun getAll(): Flow<List<StarshipEntity>>

    @Insert(onConflict = REPLACE)
    fun insertAll(vararg people: StarshipEntity)
}

@Dao
interface SpecieDao {
    @Query("SELECT * FROM specie_table")
    fun getAll(): Flow<List<SpecieEntity>>

    @Insert(onConflict = REPLACE)
    fun insertAll(vararg people: SpecieEntity)
}

object Converters {
    @TypeConverter
    fun fromString(value: String?): List<Int>? {
        return value?.split(',')?.mapNotNull { it.toIntOrNull() }
    }

    @TypeConverter
    fun fromArrayList(list: List<Int>?): String? {
        return list?.joinToString(",")
    }
}



