package com.android_academy.custompagination.storage.entities

import android.util.Log
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android_academy.custompagination.Measurements
import com.android_academy.custompagination.network.models.FilmResponse
import com.android_academy.custompagination.network.models.PersonResponse
import com.android_academy.custompagination.network.models.SpecieResponse
import com.android_academy.custompagination.network.models.StarshipResponse
import com.android_academy.custompagination.network.models.VehicleResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlin.system.measureTimeMillis

@Entity(tableName = "people_table")
data class PersonEntity(
    @PrimaryKey @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "height")
    val height: String,
    @ColumnInfo(name = "mass")
    val mass: String,
    @ColumnInfo(name = "hair_color")
    val hairColor: String,
    @ColumnInfo(name = "skin_color")
    val skinColor: String,
    @ColumnInfo(name = "eye_color")
    val eyeColor: String,
    @ColumnInfo(name = "birth_year")
    val birthYear: String,
    @ColumnInfo(name = "gender")
    val gender: String,
    @ColumnInfo(name = "homeWorld")
    val homeWorld: String,
    @ColumnInfo(name = "films")
    val films: String,
    @ColumnInfo(name = "species")
    val species: String,
    @ColumnInfo(name = "vehicles")
    val vehicles: String,
    @ColumnInfo(name = "starships")
    val starships: String,
    @ColumnInfo(name = "created")
    val created: String,
    @ColumnInfo(name = "edited")
    val edited: String,
    @ColumnInfo(name = "url")
    val url: String
)


fun PersonResponse.toPeopleEntity(
    filmList: List<FilmResponse?>,
    specieList: List<SpecieResponse?>,
    vehicleList: List<VehicleResponse?>,
    starshipList: List<StarshipResponse?>,
    moshi: Moshi
): PersonEntity {
    val start = System.currentTimeMillis()
    val films = getJson(moshi, filmList)
    val species = getJson(moshi, specieList)
    val vehicles = getJson(moshi, vehicleList)
    val starships = getJson(moshi, starshipList)
    val finish = System.currentTimeMillis() - start
    Measurements.addSerializationMeasurement(name, finish)
    return PersonEntity(
        name,
        height,
        mass,
        hairColor,
        skinColor,
        eyeColor,
        birthYear,
        gender,
        homeWorld,
        films,
        species,
        vehicles,
        starships,
        created,
        edited,
        url
    )
}

private inline fun <reified T> getJson(
    moshi: Moshi,
    list: List<T>
): String {
    val type = Types.newParameterizedType(List::class.java, T::class.java)
    val adapter = moshi.adapter<List<T>>(type)
    return adapter.toJson(list.filterNotNull())
}
