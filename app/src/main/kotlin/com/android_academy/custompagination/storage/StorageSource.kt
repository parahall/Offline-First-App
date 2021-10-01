package com.android_academy.custompagination.storage

import com.android_academy.custompagination.storage.entities.FilmEntity
import com.android_academy.custompagination.storage.entities.PersonEntity
import com.android_academy.custompagination.storage.entities.SpecieEntity
import com.android_academy.custompagination.storage.entities.StarshipEntity
import com.android_academy.custompagination.storage.entities.VehicleEntity
import kotlinx.coroutines.flow.Flow

interface StorageSource {
    fun getPeople(): Flow<List<PersonEntity>>
    fun storePeople(entities: List<PersonEntity>)
    fun getFilms(): Flow<List<FilmEntity>>
    fun storeFilms(entities: List<FilmEntity>)
    fun getVehicles(): Flow<List<VehicleEntity>>
    fun storeVehicles(entities: List<VehicleEntity>)
    fun getStarships(): Flow<List<StarshipEntity>>
    fun storeStarships(entities: List<StarshipEntity>)
    fun getSpecies(): Flow<List<SpecieEntity>>
    fun storeSpecies(entities: List<SpecieEntity>)
}

class StorageSourceImpl(private val db: StarWarsDb) : StorageSource {

    override fun getPeople(): Flow<List<PersonEntity>> {
        return db.personDao().getAll()
    }

    override fun storePeople(entities: List<PersonEntity>) {
        db.personDao().insertAll(*entities.toTypedArray())
    }

    override fun getFilms(): Flow<List<FilmEntity>> {
        return db.filmDao().getAll()
    }

    override fun storeFilms(entities: List<FilmEntity>) {
        db.filmDao().insertAll(*entities.toTypedArray())
    }

    override fun getVehicles(): Flow<List<VehicleEntity>> {
        return db.vehicleDao().getAll()
    }

    override fun storeVehicles(entities: List<VehicleEntity>) {
        db.vehicleDao().insertAll(*entities.toTypedArray())
    }

    override fun getStarships(): Flow<List<StarshipEntity>> {
        return db.starshipDao().getAll()
    }

    override fun storeStarships(entities: List<StarshipEntity>) {
        db.starshipDao().insertAll(*entities.toTypedArray())
    }

    override fun getSpecies(): Flow<List<SpecieEntity>> {
        return db.specieDao().getAll()
    }

    override fun storeSpecies(entities: List<SpecieEntity>) {
        db.specieDao().insertAll(*entities.toTypedArray())
    }
}