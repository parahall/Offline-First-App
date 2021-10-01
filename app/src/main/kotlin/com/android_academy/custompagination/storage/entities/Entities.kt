package com.android_academy.custompagination.storage.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android_academy.custompagination.models.Film
import com.android_academy.custompagination.models.Person
import com.android_academy.custompagination.models.Specie
import com.android_academy.custompagination.models.Starship
import com.android_academy.custompagination.models.Vehicle
import com.android_academy.custompagination.network.models.PersonResponse


data class EnrichedPersonEntity(
    val personEntity: PersonEntity,
    val films: List<FilmEntity>,
    val species: List<SpecieEntity>,
    val vehicles: List<VehicleEntity>,
    val starships: List<StarshipEntity>,
)

open class StorageEntity

fun EnrichedPersonEntity.toPerson(): Person {
    return Person(
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
        personEntity.url
    )
}


@Entity(tableName = "people_table")
data class PersonEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "name")
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
    @ColumnInfo(name = "films_ids")
    val filmsIds: List<Int>?,
    @ColumnInfo(name = "species_ids")
    val speciesIds: List<Int>?,
    @ColumnInfo(name = "vehicles_ids")
    val vehiclesIds: List<Int>?,
    @ColumnInfo(name = "starships_ids")
    val starshipsIds: List<Int>?,
    @ColumnInfo(name = "created")
    val created: String,
    @ColumnInfo(name = "edited")
    val edited: String,
    @ColumnInfo(name = "url")
    val url: String
) : StorageEntity()

@Entity(tableName = "films_table")
data class FilmEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    val created: String,
    val director: String,
    val edited: String,
    @ColumnInfo(name = "episode_id")
    val episodeId: Int,
    @ColumnInfo(name = "opening_crawl")
    val openingCrawl: String,
    val producer: String,
    @ColumnInfo(name = "release_date")
    val releaseDate: String,
    val title: String,
    val url: String,
) : StorageEntity()

fun FilmEntity.toModel(): Film {
    return Film(
        id = id,
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

@Entity(tableName = "specie_table")
data class SpecieEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "average_height")
    val averageHeight: String,
    @ColumnInfo(name = "average_lifespan")
    val averageLifespan: String,
    val classification: String,
    val created: String,
    val designation: String,
    val edited: String,
    val eye_colors: String,
    @ColumnInfo(name = "hair_colors")
    val hairColors: String,
    @ColumnInfo(name = "homeworld")
    val homeWorld: String?,
    val language: String,
    val name: String,
    @ColumnInfo(name = "skin_colors")
    val skinColors: String,
    val url: String
) : StorageEntity()

fun SpecieEntity.toModel(): Specie {
    return Specie(
        id = id,
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

@Entity(tableName = "vehicle_table")
data class VehicleEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "cargo_capacity")
    val cargoCapacity: String,
    val consumables: String,
    @ColumnInfo(name = "cost_in_credits")
    val costInCredits: String,
    val created: String,
    val crew: String,
    val edited: String,
    val length: String,
    val manufacturer: String,
    @ColumnInfo(name = "max_atmosphering_speed")
    val maxAtmosphericSpeed: String,
    val model: String,
    val name: String,
    val passengers: String,
    val url: String,
    @ColumnInfo(name = "vehicle_class")
    val vehicleClass: String
) : StorageEntity()

fun VehicleEntity.toModel(): Vehicle {
    return Vehicle(
        id = id,
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

@Entity(tableName = "starship_table")
data class StarshipEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    val mglt: String,
    @ColumnInfo(name = "cargo_capacity")
    val cargoCapacity: String,
    val consumables: String,
    @ColumnInfo(name = "cost_in_credits")
    val costInCredits: String,
    val created: String,
    val crew: String,
    val edited: String,
    @ColumnInfo(name = "hyperdrive_rating")
    val hyperdriveRating: String,
    val length: String,
    val manufacturer: String,
    @ColumnInfo(name = "max_atmosphering_speed")
    val maxAtmosphericSpeed: String,
    val model: String,
    val name: String,
    val passengers: String,
    @ColumnInfo(name = "starship_class")
    val starshipClass: String,
    val url: String
) : StorageEntity()


fun StarshipEntity.toModel(): Starship {
    return Starship(
        id,
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

fun PersonResponse.toPeopleEntity(): PersonEntity {
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

fun String.extractId(): Int {
    return this.split("/").dropLast(1).last().toInt()
}

//private inline fun <reified T> getJson(
//    moshi: Moshi,
//    list: List<T>
//): String {
//    val type = Types.newParameterizedType(List::class.java, T::class.java)
//    val adapter = moshi.adapter<List<T>>(type)
//    return adapter.toJson(list.filterNotNull())
//}
