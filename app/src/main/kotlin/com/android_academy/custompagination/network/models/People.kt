package com.android_academy.custompagination.network.models

import com.android_academy.custompagination.models.Film
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PagedResponse<T>(
    @Json(name = "count")
    val count: Long,
    @Json(name = "next")
    val next: String?,
    @Json(name = "previous")
    val previous: String? = null,
    @Json(name = "results")
    val results: List<T>
)

@JsonClass(generateAdapter = true)
data class PersonResponse(
    @Json(name = "name")
    val name: String,
    @Json(name = "height")
    val height: String,
    @Json(name = "mass")
    val mass: String,
    @Json(name = "hair_color")
    val hairColor: String,
    @Json(name = "skin_color")
    val skinColor: String,
    @Json(name = "eye_color")
    val eyeColor: String,
    @Json(name = "birth_year")
    val birthYear: String,
    @Json(name = "gender")
    val gender: String,
    @Json(name = "homeworld")
    val homeWorld: String,
    @Json(name = "films")
    val films: List<String>,
    @Json(name = "species")
    val species: List<String>,
    @Json(name = "vehicles")
    val vehicles: List<String>,
    @Json(name = "starships")
    val starships: List<String>,
    @Json(name = "created")
    val created: String,
    @Json(name = "edited")
    val edited: String,
    @Json(name = "url")
    val url: String
)

@JsonClass(generateAdapter = true)
data class FilmResponse(
    val characters: List<String>,
    val created: String,
    val director: String,
    val edited: String,
    val episode_id: Int,
    @Json(name = "opening_crawl")
    val openingCrawl: String,
    val planets: List<String>?,
    val producer: String,
    @Json(name = "release_date")
    val releaseDate: String,
    val species: List<String>?,
    val starships: List<String>?,
    val title: String,
    val url: String,
    val vehicles: List<String>?
)

fun FilmResponse.toModel(): Film =
    Film(
        characters,
        created,
        director,
        edited,
        episode_id,
        openingCrawl,
        planets,
        producer,
        releaseDate,
        species,
        starships,
        title,
        url,
        vehicles
    )

@JsonClass(generateAdapter = true)
data class SpecieResponse(
    @Json(name = "average_height")
    val averageHeight: String,
    @Json(name = "average_lifespan")
    val averageLifespan: String,
    val classification: String,
    val created: String,
    val designation: String,
    val edited: String,
    val eye_colors: String,
    val films: List<String>?,
    val hair_colors: String,
    @Json(name = "homeworld")
    val homeWorld: String?,
    val language: String,
    val name: String,
    val people: List<String>?,
    @Json(name = "skin_colors")
    val skinColors: String,
    val url: String
)

@JsonClass(generateAdapter = true)
data class VehicleResponse(
    @Json(name = "cargo_capacity")
    val cargoCapacity: String,
    val consumables: String,
    @Json(name = "cost_in_credits")
    val costInCredits: String,
    val created: String,
    val crew: String,
    val edited: String,
    val films: List<String>,
    val length: String,
    val manufacturer: String,
    @Json(name = "max_atmosphering_speed")
    val maxAtmosphericSpeed: String,
    val model: String,
    val name: String,
    val passengers: String,
    val pilots: List<String>,
    val url: String,
    @Json(name = "vehicle_class")
    val vehicleClass: String
)

@JsonClass(generateAdapter = true)
data class StarshipResponse(
    val MGLT: String,
    @Json(name = "cargo_capacity")
    val cargoCapacity: String,
    val consumables: String,
    @Json(name = "cost_in_credits")
    val costInCredits: String,
    val created: String,
    val crew: String,
    val edited: String,
    val films: List<String>,
    @Json(name = "hyperdrive_rating")
    val hyperdriveRating: String,
    val length: String,
    val manufacturer: String,
    @Json(name = "max_atmosphering_speed")
    val maxAtmosphericSpeed: String,
    val model: String,
    val name: String,
    val passengers: String,
    val pilots: List<String>,
    @Json(name = "starship_class")
    val starshipClass: String,
    val url: String
)