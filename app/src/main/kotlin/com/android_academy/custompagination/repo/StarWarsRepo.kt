package com.android_academy.custompagination.repo

import android.util.Log
import com.android_academy.custompagination.network.NetworkSource
import com.android_academy.custompagination.network.models.EntityConvertible
import com.android_academy.custompagination.network.models.PagedResponse
import com.android_academy.custompagination.storage.StorageSource
import com.android_academy.custompagination.storage.entities.EnrichedPersonEntity
import com.android_academy.custompagination.storage.entities.FilmEntity
import com.android_academy.custompagination.storage.entities.PersonEntity
import com.android_academy.custompagination.storage.entities.SpecieEntity
import com.android_academy.custompagination.storage.entities.StarshipEntity
import com.android_academy.custompagination.storage.entities.StorageEntity
import com.android_academy.custompagination.storage.entities.VehicleEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

class StarWarsRepo(
    private val storageSource: StorageSource,
    private val networkSource: NetworkSource,
) {

    companion object {
        const val TAG = "StarWarsRepo"
    }

    suspend fun loadData(): Flow<List<EnrichedPersonEntity>> {
//        fetchAllEntities()
        Log.d(TAG, "loadData: I'm here after fetch dispatched")

        return combine(
            storageSource.getPeople(),
            storageSource.getFilms(),
            storageSource.getSpecies(),
            storageSource.getVehicles(),
            storageSource.getStarships()
        ) { people: List<PersonEntity>, films: List<FilmEntity>, species: List<SpecieEntity>, vehicles: List<VehicleEntity>, starships: List<StarshipEntity> ->
            people.map { person ->
                EnrichedPersonEntity(
                    person,
                    films.filter { film ->
                        person.filmsIds?.contains(film.id) == true
                    },
                    species.filter { film ->
                        person.speciesIds?.contains(film.id) == true
                    },
                    vehicles.filter { film ->
                        person.vehiclesIds?.contains(film.id) == true
                    },
                    starships.filter { film ->
                        person.starshipsIds?.contains(film.id) == true
                    },
                )
            }
        }
    }

    private suspend fun fetchAllEntities() {
        fetchEntities({ page -> networkSource.fetchPeople(page) }, { entities ->
            storageSource.storePeople(entities.filterIsInstance<PersonEntity>())
        })

        fetchEntities({ page -> networkSource.fetchFilms(page) }, { entities ->
            storageSource.storeFilms(entities.filterIsInstance<FilmEntity>())
        })

        fetchEntities({ page -> networkSource.fetchVehicles(page) }, { entities ->
            storageSource.storeVehicles(entities.filterIsInstance<VehicleEntity>())
        })

        fetchEntities({ page -> networkSource.fetchSpecies(page) }, { entities ->
            storageSource.storeSpecies(entities.filterIsInstance<SpecieEntity>())
        })

        fetchEntities({ page -> networkSource.fetchStarships(page) }, { entities ->
            storageSource.storeStarships(entities.filterIsInstance<StarshipEntity>())
        })
    }


    private suspend fun <T : EntityConvertible> fetchEntities(
        networkSourceCallable: suspend (Int) -> Result<PagedResponse<T>>,
        storageSourceFunction: suspend (List<StorageEntity>) -> Unit
    ) {
        val context = coroutineContext + Dispatchers.IO
        val scope = CoroutineScope(context)
        scope.launch {
            var hasNext = true
            var pageNumb = 1
            while (hasNext) {
                hasNext = fetchPage(pageNumb, networkSourceCallable, storageSourceFunction)
                pageNumb++
            }
        }
    }


    private suspend fun <T : EntityConvertible> fetchPage(
        pageNumb: Int,
        fetchFunction: suspend (Int) -> Result<PagedResponse<T>>,
        storageFunction: suspend (List<StorageEntity>) -> Unit
    ): Boolean {
        val pageResponseResult = fetchFunction.invoke(pageNumb)
        pageResponseResult.getOrNull()?.let { page ->
            Log.i(TAG, "fetchPage: page $pageNumb fetched")
            val entities = page.results.map { it.toEntity() }
            storageFunction.invoke(entities)
            return page.next?.isNotEmpty() == true
        } ?: run {
            Log.w(TAG, "fetchPage: error on page $pageNumb")
            return false
        }
    }

}
