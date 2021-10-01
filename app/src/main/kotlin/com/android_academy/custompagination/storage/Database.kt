package com.android_academy.custompagination.storage

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.RoomDatabase
import com.android_academy.custompagination.storage.entities.PersonEntity
import kotlinx.coroutines.flow.Flow

@Database(entities = [PersonEntity::class], version = 1, exportSchema = false)
abstract class StarWarsDb : RoomDatabase() {
    abstract fun personDao(): PersonDao
}

@Dao
interface PersonDao {
    @Query("SELECT * FROM people_table")
    fun getAll(): Flow<List<PersonEntity>>

    @Insert(onConflict = REPLACE)
    fun insertAll(vararg people: PersonEntity)
}




