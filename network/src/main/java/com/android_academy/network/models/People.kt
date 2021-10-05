package com.android_academy.network.models

import com.android_academy.storage.entities.FilmEntity
import com.android_academy.storage.entities.PersonEntity
import com.android_academy.storage.entities.SpecieEntity
import com.android_academy.storage.entities.StarshipEntity
import com.android_academy.storage.entities.StorageEntity
import com.android_academy.storage.entities.VehicleEntity
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

interface EntityConvertible {
    fun toEntity(): StorageEntity
}

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
    val filmsUrls: List<String>,
    @Json(name = "species")
    val speciesUrls: List<String>,
    @Json(name = "vehicles")
    val vehiclesUrls: List<String>,
    @Json(name = "starships")
    val starshipsUrls: List<String>,
    @Json(name = "created")
    val created: String,
    @Json(name = "edited")
    val edited: String,
    @Json(name = "url")
    val url: String
) : EntityConvertible {
    override fun toEntity(): StorageEntity {
        return PersonEntity(
            url.extractId(),
            name,
            height,
            mass,
            hairColor,
            skinColor,
            eyeColor,
            birthYear,
            gender,
            homeWorld,
            filmsUrls.map { it.extractId() },
            speciesUrls.map { it.extractId() },
            vehiclesUrls.map { it.extractId() },
            starshipsUrls.map { it.extractId() },
            created,
            edited,
            url
        )
    }
}

@JsonClass(generateAdapter = true)
data class FilmResponse(
    val characters: List<String>,
    val created: String,
    val director: String,
    val edited: String,
    @Json(name = "episode_id")
    val episodeId: Int,
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
) : EntityConvertible {
    override fun toEntity(): StorageEntity {
        return FilmEntity(
            filmId = url.extractId(),
            created = created,
            director = director,
            edited = edited,
            episodeId = episodeId,
            openingCrawl = openingCrawl,
            producer = producer,
            releaseDate = releaseDate,
            title = title,
            url = url
        )
    }
}


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
    @Json(name = "hair_colors")
    val hairColors: String,
    @Json(name = "homeworld")
    val homeWorld: String?,
    val language: String,
    val name: String,
    val people: List<String>?,
    @Json(name = "skin_colors")
    val skinColors: String,
    val url: String
) : EntityConvertible {
    override fun toEntity(): StorageEntity {
        return SpecieEntity(
            specieId = url.extractId(),
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
}

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
) : EntityConvertible {
    override fun toEntity(): StorageEntity {
        return VehicleEntity(
            vehicleId = url.extractId(),
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
}

@JsonClass(generateAdapter = true)
data class StarshipResponse(
    @Json(name = "MGLT")
    val mglt: String,
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
) : EntityConvertible {
    override fun toEntity(): StorageEntity {
        return StarshipEntity(
            starshipId = url.extractId(),
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
            hyperdriveRating = hyperdriveRating,
            mglt = mglt,
            starshipClass = starshipClass
        )
    }
}

fun String.extractId(): Int {
    return this.split("/").dropLast(1).last().toInt()
}