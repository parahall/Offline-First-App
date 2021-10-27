package com.android_academy.offline_first_app.models

import com.android_academy.storage.entities.EnrichedPersonEntity
import com.android_academy.storage.entities.FilmEntity
import com.android_academy.storage.entities.SpecieEntity
import com.android_academy.storage.entities.StarshipEntity
import com.android_academy.storage.entities.VehicleEntity


fun FilmEntity.toModel(): Film {
    return Film(
        id = filmId,
        created = created,
        director = director,
        edited = edited,
        episode_id = episodeId,
        openingCrawl = openingCrawl,
        producer = producer,
        releaseDate = releaseDate,
        title = title,
        url = url,
    )
}

fun EnrichedPersonEntity.toPerson(): Person {
    return Person(
        personEntity.personId,
        personEntity.name,
        personEntity.height,
        personEntity.mass,
        personEntity.hairColor,
        personEntity.skinColor,
        personEntity.eyeColor,
        personEntity.birthYear,
        personEntity.gender,
        personEntity.homeWorld,
        films.map { it.toModel() },
        species.map { it.toModel() },
        vehicles.map { it.toModel() },
        starships.map { it.toModel() },
        personEntity.created,
        personEntity.edited,
        personEntity.url,
        favoriteProps?.isFavorite ?: false
    )
}


fun SpecieEntity.toModel(): Specie {
    return Specie(
        id = specieId,
        averageHeight = averageHeight,
        averageLifespan = averageLifespan,
        classification = classification,
        created = created,
        designation = designation,
        edited = edited,
        eye_colors = eye_colors,
        hairColors = hairColors,
        homeWorld = homeWorld,
        language = language,
        name = name,
        skinColors = skinColors,
        url = url
    )
}

fun VehicleEntity.toModel(): Vehicle {
    return Vehicle(
        id = vehicleId,
        cargoCapacity = cargoCapacity,
        consumables = consumables,
        costInCredits = costInCredits,
        created = created,
        crew = crew,
        edited = edited,
        length = length,
        manufacturer = manufacturer,
        maxAtmosphericSpeed = maxAtmosphericSpeed,
        model = model,
        name = name,
        passengers = passengers,
        url = url,
        vehicleClass = vehicleClass

    )
}

fun StarshipEntity.toModel(): Starship {
    return Starship(
        starshipId,
        mglt,
        cargoCapacity,
        consumables,
        costInCredits,
        created,
        crew,
        edited,
        hyperdriveRating,
        length,
        manufacturer,
        maxAtmosphericSpeed,
        model,
        name,
        passengers,
        starshipClass,
        url
    )
}


data class Person(
    val personId: Int,
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
    val url: String,
    val isFavor: Boolean
)

data class Film(
    val id: Int,
    val created: String,
    val director: String,
    val edited: String,
    val episode_id: Int,
    val openingCrawl: String,
    val producer: String,
    val releaseDate: String,
    val title: String,
    val url: String,
)

data class Specie(
    val id: Int,
    val averageHeight: String,
    val averageLifespan: String,
    val classification: String,
    val created: String,
    val designation: String,
    val edited: String,
    val eye_colors: String,
    val hairColors: String,
    val homeWorld: String?,
    val language: String,
    val name: String,
    val skinColors: String,
    val url: String
)

data class Vehicle(
    val id: Int,
    val cargoCapacity: String,
    val consumables: String,
    val costInCredits: String,
    val created: String,
    val crew: String,
    val edited: String,
    val length: String,
    val manufacturer: String,
    val maxAtmosphericSpeed: String,
    val model: String,
    val name: String,
    val passengers: String,
    val url: String,
    val vehicleClass: String
)

data class Starship(
    val id: Int,
    val mglt: String,
    val cargoCapacity: String,
    val consumables: String,
    val costInCredits: String,
    val created: String,
    val crew: String,
    val edited: String,
    val hyperdriveRating: String,
    val length: String,
    val manufacturer: String,
    val maxAtmosphericSpeed: String,
    val model: String,
    val name: String,
    val passengers: String,
    val starshipClass: String,
    val url: String
)