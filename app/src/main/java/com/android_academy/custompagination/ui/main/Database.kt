package com.android_academy.custompagination.ui.main

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

@Database(entities = [Item::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao
}

@Entity
data class Item(
    @PrimaryKey @ColumnInfo(name = "item_name") val name: String,
)

@Dao
interface ItemDao {
    @Query("SELECT * FROM item")
    fun getAll(): Flow<List<Item>>

    @Insert(onConflict = REPLACE)
    fun insertAll(vararg items: Item)
}

