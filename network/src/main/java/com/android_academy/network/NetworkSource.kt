package com.android_academy.network

import com.android_academy.network.models.FilmResponse
import com.android_academy.network.models.PagedResponse
import com.android_academy.network.models.PersonResponse
import com.android_academy.network.models.SpecieResponse
import com.android_academy.network.models.StarshipResponse
import com.android_academy.network.models.VehicleResponse
import retrofit2.Response

interface NetworkSource {
    suspend fun fetchPeople(pageNumb: Int): Result<PagedResponse<PersonResponse>>
    suspend fun fetchVehicles(pageNumb: Int): Result<PagedResponse<VehicleResponse>>
    suspend fun fetchStarships(pageNumb: Int): Result<PagedResponse<StarshipResponse>>
    suspend fun fetchSpecies(pageNumb: Int): Result<PagedResponse<SpecieResponse>>
    suspend fun fetchFilms(pageNumb: Int): Result<PagedResponse<FilmResponse>>
    suspend fun getFilm(id: String): Response<FilmResponse>
    suspend fun getVehicle(id: String): Response<VehicleResponse>
    suspend fun getSpecie(id: String): Response<SpecieResponse>
    suspend fun getStarship(id: String): Response<StarshipResponse>
}


class NetworkSourceImpl(private val api: StarWarsApi) : NetworkSource {

    override suspend fun fetchPeople(pageNumb: Int): Result<PagedResponse<PersonResponse>> {
        return safeResultApiCall { api.getPeople(pageNumb) }
    }

    override suspend fun fetchVehicles(pageNumb: Int): Result<PagedResponse<VehicleResponse>> {
        return safeResultApiCall { api.getVehicles(pageNumb) }
    }

    override suspend fun fetchStarships(pageNumb: Int): Result<PagedResponse<StarshipResponse>> {
        return safeResultApiCall { api.getStarships(pageNumb) }
    }

    override suspend fun fetchSpecies(pageNumb: Int): Result<PagedResponse<SpecieResponse>> {
        return safeResultApiCall { api.getSpecies(pageNumb) }
    }

    override suspend fun fetchFilms(pageNumb: Int): Result<PagedResponse<FilmResponse>> {
        return safeResultApiCall { api.getFilms(pageNumb) }
    }

    override suspend fun getFilm(id: String) = api.getFilm(id)

    override suspend fun getVehicle(id: String) = api.getVehicle(id)

    override suspend fun getStarship(id: String) = api.getStarship(id)

    override suspend fun getSpecie(id: String) = api.getSpecie(id)

}