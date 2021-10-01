package com.android_academy.custompagination.repo

import android.util.Log
import com.android_academy.custompagination.network.NetworkSource
import com.android_academy.custompagination.network.StarWarsApi
import com.android_academy.custompagination.network.models.FilmResponse
import com.android_academy.custompagination.network.models.PersonResponse
import com.android_academy.custompagination.network.models.SpecieResponse
import com.android_academy.custompagination.network.models.StarshipResponse
import com.android_academy.custompagination.network.models.VehicleResponse
import com.android_academy.custompagination.storage.StorageSource
import com.android_academy.custompagination.storage.entities.PersonEntity
import com.android_academy.custompagination.storage.entities.toPeopleEntity
import com.squareup.moshi.Moshi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.Response
import kotlin.coroutines.coroutineContext

class StarWarsRepo(
    private val storageSource: StorageSource,
    private val networkSource: NetworkSource,
    private val moshi: Moshi
) {

    private val fetchedFilms: MutableMap<String, FilmResponse> = mutableMapOf()
    private val fetchedSpecies: MutableMap<String, SpecieResponse> = mutableMapOf()
    private val fetchedVehicles: MutableMap<String, VehicleResponse> = mutableMapOf()
    private val fetchedStarships: MutableMap<String, StarshipResponse> = mutableMapOf()

    companion object {
        const val TAG = "StarWarsRepo"
    }

    suspend fun loadData(): Flow<List<PersonEntity>> {
        fetchPeople()
        Log.d(TAG, "loadData: I'm here after fetch dispatched")

        return storageSource.getPeople()
    }

    private suspend fun fetchPeople() {
        val context = coroutineContext + Dispatchers.IO
        val scope = CoroutineScope(context)
        scope.launch {
            var hasNext = true
            var pageNumb = 1
            while (hasNext) {
                hasNext = fetchPeoplePage(pageNumb)
                pageNumb++
            }
            fetchedFilms.clear()
            fetchedSpecies.clear()
            fetchedStarships.clear()
            fetchedVehicles.clear()
        }
    }

    private suspend fun fetchPeoplePage(pageNumb: Int): Boolean {
        val pageResponseResult = networkSource.fetchPeople(pageNumb)
        Log.d(TAG, "fetchPeopleData: people fetched. page: $pageNumb")
        pageResponseResult.getOrNull()?.let { page ->
            val entities = page.results.map { enrichPerson(it) }
            storageSource.storePeople(entities)
            return page.next?.isNotEmpty() == true
        } ?: run {
            return false
        }
    }

    private suspend fun enrichPerson(personResponse: PersonResponse): PersonEntity {
        return coroutineScope {
            val job = async {
                val filmsIds = getIds(personResponse.films)
                val speciesIds = getIds(personResponse.species)
                val vehiclesIds = getIds(personResponse.vehicles)
                val starshipsIds = getIds(personResponse.starships)

                populateEnrichDataCache(
                    filmsIds,
                    { id -> networkSource.getFilm(id)},
                    fetchedFilms
                )

                val filmList = getCachedDataList(filmsIds, fetchedFilms)
                populateEnrichDataCache(
                    speciesIds,
                    { id -> networkSource.getSpecie(id) },
                    fetchedSpecies
                )
                val specieList = getCachedDataList(speciesIds, fetchedSpecies)
                populateEnrichDataCache(
                    vehiclesIds,
                    { id -> networkSource.getVehicle(id) },
                    fetchedVehicles
                )
                val vehicleList = getCachedDataList(vehiclesIds, fetchedVehicles)
                populateEnrichDataCache(
                    starshipsIds,
                    { id -> networkSource.getStarship(id) },
                    fetchedStarships
                )
                val starshipList = getCachedDataList(starshipsIds, fetchedStarships)

                personResponse.toPeopleEntity(
                    filmList,
                    specieList,
                    vehicleList,
                    starshipList,
                    moshi
                )
            }
            return@coroutineScope job.await()
        }
    }

    private suspend fun <T> populateEnrichDataCache(
        ids: List<String>,
        callFunction: suspend (id: String) -> Response<T>,
        cache: MutableMap<String, T>
    ) {
        ids.filter { !cache.containsKey(it) }.map { id ->
            val result = networkSource.fetchModel(callFunction, id)
            result.getOrNull()?.let {
                cache.put(id, it)
            }
        }
    }

    private fun <T> getCachedDataList(ids: List<String>, cache: Map<String, T>) =
        cache.filter { ids.contains(it.key) }.values.toList()


    private fun getIds(urls: List<String>) =
        urls.map { it.split('/').dropLast(1).last() }

}
