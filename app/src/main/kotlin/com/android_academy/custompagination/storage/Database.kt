package com.android_academy.custompagination.ui.main

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.RoomDatabase
import com.android_academy.custompagination.storage.entities.PeopleEntity
import kotlinx.coroutines.flow.Flow

@Database(entities = [PeopleEntity::class], version = 1, exportSchema = false)
abstract class StarWarsDb : RoomDatabase() {
    abstract fun peopleDao(): PeopleDao
}

@Dao
interface PeopleDao {
    @Query("SELECT * FROM people_table")
    fun getAll(): Flow<List<PeopleEntity>>

    @Insert(onConflict = REPLACE)
    fun insertAll(vararg people: PeopleEntity)
}




