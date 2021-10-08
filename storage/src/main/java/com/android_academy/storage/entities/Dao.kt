package com.android_academy.storage.entities

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow


@Dao
interface RemoteSyncDataDao {
    @Insert
    suspend fun insert(boardRemoteData: PersistedRemoteDataEntity)
}


@Dao
interface FavoritesDao {
    @Transaction
    fun toggleFavorites(personId: Int): Boolean {
        val isFavor = getFavoriteStatus(personId) ?: false
        insert(FavoritePersonEntity(personId, !isFavor))
        return !isFavor
    }

    @Query("SELECT is_favorite FROM favorite_people_table WHERE person_id = :personId")
    fun getFavoriteStatus(personId: Int): Boolean?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: FavoritePersonEntity)
}

@Dao
interface EnrichDao {
    @Transaction
    @Query("SELECT * FROM people_table")
    fun getAll(): Flow<List<EnrichedPersonEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg crossRef: PersonFilmsCrossRef)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg crossRef: PersonSpecieCrossRef)

    @Insert(onConflict = OnConflictStrategy.IGNORE)

    fun insertAll(vararg crossRef: PersonVehicleCrossRef)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg crossRef: PersonStarshipCrossRef)
}

@Dao
interface PersonDao {
    @Query("SELECT * FROM people_table")
    fun getAll(): Flow<List<PersonEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg people: PersonEntity)

    @Query("SELECT * FROM people_table")
    fun getAllSync(): List<PersonEntity>
}

@Dao
interface FilmDao {
    @Query("SELECT * FROM films_table")
    fun getAll(): Flow<List<FilmEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg people: FilmEntity)
}

@Dao
interface VehicleDao {
    @Query("SELECT * FROM vehicle_table")
    fun getAll(): Flow<List<VehicleEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg people: VehicleEntity)
}

@Dao
interface StarshipDao {
    @Query("SELECT * FROM starship_table")
    fun getAll(): Flow<List<StarshipEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg people: StarshipEntity)
}

@Dao
interface SpecieDao {
    @Query("SELECT * FROM specie_table")
    fun getAll(): Flow<List<SpecieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg people: SpecieEntity)
}

