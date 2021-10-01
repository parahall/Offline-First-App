package com.android_academy.custompagination.storage

import com.android_academy.custompagination.storage.entities.PersonEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

interface StorageSource {
    fun getPeople() : Flow<List<PersonEntity>>
    fun storePeople(entities: List<PersonEntity>)
}

class StorageSourceImpl(private val db: StarWarsDb) : StorageSource {

    override fun getPeople() : Flow<List<PersonEntity>> {
        return db.personDao().getAll().distinctUntilChanged()
    }

    override fun storePeople(entities: List<PersonEntity>) {
        db.personDao().insertAll(*entities.toTypedArray())
    }
}