package com.android_academy.custompagination.network

import com.android_academy.custompagination.network.models.FilmResponse
import com.android_academy.custompagination.network.models.PagedResponse
import com.android_academy.custompagination.network.models.PersonResponse
import com.android_academy.custompagination.network.models.SpecieResponse
import com.android_academy.custompagination.network.models.StarshipResponse
import com.android_academy.custompagination.network.models.VehicleResponse
import retrofit2.Response

interface NetworkSource {
    suspend fun fetchPeople(pageNumb: Int): Result<PagedResponse<PersonResponse>>
    suspend fun getFilm(id: String): Response<FilmResponse>
    suspend fun getVehicle(id: String): Response<VehicleResponse>
    suspend fun getSpecie(id: String): Response<SpecieResponse>
    suspend fun getStarship(id: String): Response<StarshipResponse>
    suspend fun <T> fetchModel(
        callFunction: suspend (id: String) -> Response<T>,
        id: String
    ): Result<T>
}


class NetworkSourceImpl(private val api: StarWarsApi) : NetworkSource {

    override suspend fun fetchPeople(pageNumb: Int): Result<PagedResponse<PersonResponse>> {
        return safeResultApiCall { api.getPeople(pageNumb) }
    }

    override suspend fun <T> fetchModel(
        callFunction: suspend (id: String) -> Response<T>,
        id: String
    ): Result<T> {
        return safeResultApiCall { callFunction(id) }
    }

    override suspend fun getFilm(id: String) = api.getFilm(id)

    override suspend fun getVehicle(id: String) = api.getVehicle(id)

    override suspend fun getStarship(id: String) = api.getStarship(id)

    override suspend fun getSpecie(id: String) = api.getSpecie(id)

}