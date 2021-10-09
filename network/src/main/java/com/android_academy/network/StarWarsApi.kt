package com.android_academy.network

import com.android_academy.network.models.FilmResponse
import com.android_academy.network.models.PagedResponse
import com.android_academy.network.models.PersonResponse
import com.android_academy.network.models.SpecieResponse
import com.android_academy.network.models.StarshipResponse
import com.android_academy.network.models.VehicleResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface StarWarsApi {

    @GET("people/")
    suspend fun getPeople(@Query("page") page: Int): Response<PagedResponse<PersonResponse>>

    @GET("films/")
    suspend fun getFilms(@Query("page") page: Int): Response<PagedResponse<FilmResponse>>

    @GET("species/")
    suspend fun getSpecies(@Query("page") page: Int): Response<PagedResponse<SpecieResponse>>

    @GET("vehicles/")
    suspend fun getVehicles(@Query("page") page: Int): Response<PagedResponse<VehicleResponse>>

    @GET("starships/")
    suspend fun getStarships(@Query("page") page: Int): Response<PagedResponse<StarshipResponse>>

    @GET("films/{id}/")
    suspend fun getFilm(@Path("id") id: String): Response<FilmResponse>

    @GET("species/{id}/")
    suspend fun getSpecie(@Path("id") specieId: String): Response<SpecieResponse>

    @GET("vehicles/{id}/")
    suspend fun getVehicle(@Path("id") vehicleId: String): Response<VehicleResponse>

    @GET("starships/{id}/")
    suspend fun getStarship(@Path("id") specieId: String): Response<StarshipResponse>

    @POST("person/{id}/")
    suspend fun markPersonFavorite(
        @Path("id") personId: Int,
        @Query("is_favor") favor: Boolean
    ): Response<Unit>
}