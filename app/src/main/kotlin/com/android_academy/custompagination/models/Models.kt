package com.android_academy.custompagination.models

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