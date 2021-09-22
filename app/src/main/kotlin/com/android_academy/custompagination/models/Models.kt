package com.android_academy.custompagination.models

import com.android_academy.custompagination.storage.entities.PeopleEntity

data class People(
    val name: String,
    val height: String,
    val mass: String,
    val hairColor: String,
    val skinColor: String,
    val eyeColor: String,
    val birthYear: String,
    val gender: String,
    val homeWorld: String,
    val films: List<String>,
    val species: List<String>,
    val vehicles: List<String>,
    val starships: List<String>,
    val created: String,
    val edited: String,
    val url: String
)

fun PeopleEntity.toPeople(): People {
    return People(
        name,
        height,
        mass,
        hairColor,
        skinColor,
        eyeColor,
        birthYear,
        gender,
        homeWorld,
        films.split(","),
        species.split(","),
        vehicles.split(","),
        starships.split(","),
        created,
        edited,
        url
    )
}