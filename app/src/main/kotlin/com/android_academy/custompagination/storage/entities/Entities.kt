package com.android_academy.custompagination.storage.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android_academy.custompagination.network.models.PeopleResponse

@Entity(tableName = "people_table")
data class PeopleEntity(
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
    @ColumnInfo(name = "homeworld")
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


fun PeopleResponse.toPeopleEntity(): PeopleEntity {
    return PeopleEntity(
        name,
        height,
        mass,
        hairColor,
        skinColor,
        eyeColor,
        birthYear,
        gender,
        homeworld,
        films.joinToString(","),
        species.joinToString(","),
        vehicles.joinToString(","),
        starships.joinToString(","),
        created,
        edited,
        url
    )
}
