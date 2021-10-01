package com.android_academy.custompagination.models

import com.android_academy.custompagination.Measurements
import com.android_academy.custompagination.network.models.FilmResponse
import com.android_academy.custompagination.network.models.SpecieResponse
import com.android_academy.custompagination.network.models.StarshipResponse
import com.android_academy.custompagination.network.models.VehicleResponse
import com.android_academy.custompagination.network.models.toModel
import com.android_academy.custompagination.storage.entities.PersonEntity
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

data class Person(
    val name: String,
    val height: String,
    val mass: String,
    val hairColor: String,
    val skinColor: String,
    val eyeColor: String,
    val birthYear: String,
    val gender: String,
    val homeWorld: String,
    val films: List<Film>,
    val species: List<Specie>,
    val vehicles: List<Vehicle>,
    val starships: List<Starship>,
    val created: String,
    val edited: String,
    val url: String
)

fun PersonEntity.toPerson(moshi: Moshi): Person {
    val start = System.currentTimeMillis()
    val films = getResponse(this.films, moshi, FilmResponse::class.java).map { it.toModel() }
    val speciesModel =
        getResponse(this.species, moshi, SpecieResponse::class.java).map { it.toModel() }
    val vehiclesModel =
        getResponse(this.vehicles, moshi, VehicleResponse::class.java).map { it.toModel() }
    val starshipsModel =
        getResponse(this.starships, moshi, StarshipResponse::class.java).map { it.toModel() }
    val finish = System.currentTimeMillis() - start
    Measurements.addDeserializationMeasurement(name, finish)
    return Person(
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
        speciesModel,
        vehiclesModel,
        starshipsModel,
        created,
        edited,
        url
    )
}

private fun StarshipResponse.toModel(): Starship = Starship(
    MGLT,
    cargoCapacity,
    consumables,
    costInCredits,
    created,
    crew,
    edited,
    films,
    hyperdriveRating,
    length,
    manufacturer,
    maxAtmosphericSpeed,
    model,
    name,
    passengers,
    pilots,
    starshipClass,
    url
)

private fun SpecieResponse.toModel(): Specie = Specie(
    averageHeight,
    averageLifespan,
    classification,
    created,
    designation,
    edited,
    eye_colors,
    films,
    hair_colors,
    homeWorld,
    language,
    name,
    people,
    skinColors,
    url
)

private fun VehicleResponse.toModel(): Vehicle =
    Vehicle(
        cargoCapacity,
        consumables,
        costInCredits,
        created,
        crew,
        edited,
        films,
        length,
        manufacturer,
        maxAtmosphericSpeed,
        model,
        name,
        passengers,
        pilots,
        url,
        vehicleClass
    )


private inline fun <reified T> getResponse(
    stringList: String,
    moshi: Moshi,
    java: Class<T>
): List<T> {
    val type = Types.newParameterizedType(List::class.java, T::class.java)
    val adapter = moshi.adapter<List<T>>(type)
    return adapter.fromJson(stringList) ?: emptyList()
}

data class Film(
    val characters: List<String>,
    val created: String,
    val director: String,
    val edited: String,
    val episode_id: Int,
    val openingCrawl: String,
    val planets: List<String>?,
    val producer: String,
    val releaseDate: String,
    val species: List<String>?,
    val starships: List<String>?,
    val title: String,
    val url: String,
    val vehicles: List<String>?
)

data class Specie(
    val averageHeight: String,
    val averageLifespan: String,
    val classification: String,
    val created: String,
    val designation: String,
    val edited: String,
    val eye_colors: String,
    val films: List<String>?,
    val hair_colors: String,
    val homeWorld: String?,
    val language: String,
    val name: String,
    val people: List<String>?,
    val skinColors: String,
    val url: String
)

data class Vehicle(
    val cargoCapacity: String,
    val consumables: String,
    val costInCredits: String,
    val created: String,
    val crew: String,
    val edited: String,
    val films: List<String>,
    val length: String,
    val manufacturer: String,
    val maxAtmosphericSpeed: String,
    val model: String,
    val name: String,
    val passengers: String,
    val pilots: List<String>,
    val url: String,
    val vehicleClass: String
)

data class Starship(
    val MGLT: String,
    val cargoCapacity: String,
    val consumables: String,
    val costInCredits: String,
    val created: String,
    val crew: String,
    val edited: String,
    val films: List<String>,
    val hyperdriveRating: String,
    val length: String,
    val manufacturer: String,
    val maxAtmosphericSpeed: String,
    val model: String,
    val name: String,
    val passengers: String,
    val pilots: List<String>,
    val starshipClass: String,
    val url: String
)