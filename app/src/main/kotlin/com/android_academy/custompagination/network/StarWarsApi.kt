package com.android_academy.custompagination.network

import com.android_academy.custompagination.network.models.PagedResponse
import com.android_academy.custompagination.network.models.PeopleResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface StarWarsApi {

    @GET("people/")
    suspend fun getAllPeople(@Query("page") page: Int): Response<PagedResponse<PeopleResponse>>
}