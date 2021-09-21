package com.android_academy.custompagination.ui.main

import androidx.room.Room
import com.android_academy.custompagination.PagingApp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataRepo {

    val db = Room.databaseBuilder(
        PagingApp.getAppContext(),
        AppDatabase::class.java, "database-name"
    ).build()

    fun loadData(): Flow<List<Item>> {
        addData()
        return db.itemDao().getAll()
    }

    private fun addData() {
        val list = mutableListOf<Item>()
        repeat(1000) {
            list.add(Item("Item $it"))
        }
        db.itemDao().insertAll(*list.toTypedArray())
    }
}
