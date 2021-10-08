package com.android_academy.storage.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.util.Date
import java.util.UUID

sealed class StorageEntity


data class EnrichedPersonEntity(
    @Embedded val personEntity: PersonEntity,
    @Relation(
        parentColumn = "person_id",
        entityColumn = "film_id",
        associateBy = Junction(PersonFilmsCrossRef::class)
    )
    val films: List<FilmEntity>,

    @Relation(
        parentColumn = "person_id",
        entityColumn = "specie_id",
        associateBy = Junction(PersonSpecieCrossRef::class)
    )
    val species: List<SpecieEntity>,

    @Relation(
        parentColumn = "person_id",
        entityColumn = "vehicle_id",
        associateBy = Junction(PersonVehicleCrossRef::class)
    )
    val vehicles: List<VehicleEntity>,
    @Relation(
        parentColumn = "person_id",
        entityColumn = "starship_id",
        associateBy = Junction(PersonStarshipCrossRef::class)
    )
    val starships: List<StarshipEntity>,

    @Relation(
        parentColumn = "person_id",
        entityColumn = "person_id"
    )
    val favoriteProps: FavoritePersonEntity?
)

@Entity(tableName = "favorite_people_table")
data class FavoritePersonEntity(
    @PrimaryKey
    @ColumnInfo(name = "person_id")
    val personId: Int,
    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean
)


@Entity(tableName = "person_film_table", primaryKeys = ["person_id", "film_id"])
data class PersonFilmsCrossRef(
    @ColumnInfo(name = "person_id")
    val personId: Int,
    @ColumnInfo(name = "film_id")
    val filmId: Int
)

@Entity(tableName = "person_specie_table", primaryKeys = ["person_id", "specie_id"])
data class PersonSpecieCrossRef(
    @ColumnInfo(name = "person_id")
    val personId: Int,
    @ColumnInfo(name = "specie_id")
    val specieId: Int
)

@Entity(tableName = "person_vehicle_table", primaryKeys = ["person_id", "vehicle_id"])
data class PersonVehicleCrossRef(
    @ColumnInfo(name = "person_id")
    val personId: Int,
    @ColumnInfo(name = "vehicle_id")
    val vehicleId: Int
)

@Entity(tableName = "person_starship_table", primaryKeys = ["person_id", "starship_id"])
data class PersonStarshipCrossRef(
    @ColumnInfo(name = "person_id")
    val personId: Int,
    @ColumnInfo(name = "starship_id")
    val starshipId: Int
)

@Entity(tableName = "people_table")
data class PersonEntity(
    @PrimaryKey
    @ColumnInfo(name = "person_id")
    val personId: Int,
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
    @ColumnInfo(name = "film_id")
    val filmId: Int,
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

@Entity(tableName = "specie_table")
data class SpecieEntity(
    @PrimaryKey
    @ColumnInfo(name = "specie_id")
    val specieId: Int,
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

@Entity(tableName = "vehicle_table")
data class VehicleEntity(
    @PrimaryKey
    @ColumnInfo(name = "vehicle_id")
    val vehicleId: Int,
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

@Entity(tableName = "starship_table")
data class StarshipEntity(
    @PrimaryKey
    @ColumnInfo(name = "starship_id")
    val starshipId: Int,
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


@Entity(
    tableName = "remote_data"
)
data class PersistedRemoteDataEntity(
    @ColumnInfo(name = "timestamp")
    val timestamp: Date,
    @ColumnInfo(name = "type")
    val type: Int,
    @ColumnInfo(name = "sync_status")
    val syncStatus: String,
    @ColumnInfo(name = "data")
    val data: String,
    @ColumnInfo(name = "metadata")
    val metadata: Map<String, String>
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = generateLocalId()
}

fun generateLocalId() = UUID.randomUUID().leastSignificantBits