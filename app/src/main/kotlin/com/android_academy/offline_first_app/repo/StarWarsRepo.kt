package com.android_academy.offline_first_app.repo

import android.util.Log
import com.android_academy.remote_syncer.RemoteService
import com.android_academy.remote_syncer.operations.toggle_favorite_person.FavoriteRemoteOperation
import com.android_academy.offline_first_app.repo.StarWarsRepo.Companion.TAG
import com.android_academy.offline_first_app.utils.CacheOnSuccess
import com.android_academy.network.NetworkSource
import com.android_academy.network.models.EntityConvertible
import com.android_academy.network.models.PagedResponse
import com.android_academy.storage.StorageSource
import com.android_academy.storage.entities.EnrichedPersonEntity
import com.android_academy.storage.entities.FilmEntity
import com.android_academy.storage.entities.PersonEntity
import com.android_academy.storage.entities.PersonFilmsCrossRef
import com.android_academy.storage.entities.PersonSpecieCrossRef
import com.android_academy.storage.entities.PersonStarshipCrossRef
import com.android_academy.storage.entities.PersonVehicleCrossRef
import com.android_academy.storage.entities.SpecieEntity
import com.android_academy.storage.entities.StarshipEntity
import com.android_academy.storage.entities.StorageEntity
import com.android_academy.storage.entities.VehicleEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

interface StarWarsRepo {
    suspend fun loadData(): Flow<List<EnrichedPersonEntity>>
    suspend fun fetchData()
    suspend fun toggleFavoriteState(personId: Int)

    companion object {
        const val TAG = "StarWarsRepo"
        const val REFRESH_DATA_MSG_TYPE = "refresh_data_star_wars"
    }

}

class StarWarsRepoImpl(
    private val storageSource: StorageSource,
    private val networkSource: NetworkSource,
    private val remoteService: RemoteService,
) : StarWarsRepo {


    private val fetchDataOperation = CacheOnSuccess {
        fetchAllEntitiesInternal()
    }

    override suspend fun loadData(): Flow<List<EnrichedPersonEntity>> {
        val context = coroutineContext + Dispatchers.Default
        val scope = CoroutineScope(context)
        scope.launch {
            fetchData()
        }
        return storageSource.getEnrichedPeople()
    }

    override suspend fun fetchData() {
        fetchDataOperation.getOrAwait()
    }

    override suspend fun toggleFavoriteState(personId: Int) {
        val isFavor = storageSource.toggleFavoriteState(personId)
        remoteService.performRemoteOperation(FavoriteRemoteOperation(personId, isFavor))
    }

    private suspend fun fetchAllEntitiesInternal() {
        val peopleJob = fetchEntities({ page -> networkSource.fetchPeople(page) }, { entities ->
            storageSource.storePeople(entities.filterIsInstance<PersonEntity>())
        })

        val filmsJob = fetchEntities({ page -> networkSource.fetchFilms(page) }, { entities ->
            storageSource.storeFilms(entities.filterIsInstance<FilmEntity>())
        })

        val vehiclesJob =
            fetchEntities({ page -> networkSource.fetchVehicles(page) }, { entities ->
                storageSource.storeVehicles(entities.filterIsInstance<VehicleEntity>())
            })

        val speciesJob =
            fetchEntities({ page -> networkSource.fetchSpecies(page) }, { entities ->
                storageSource.storeSpecies(entities.filterIsInstance<SpecieEntity>())
            })

        val starshipsJob =
            fetchEntities({ page -> networkSource.fetchStarships(page) }, { entities ->
                storageSource.storeStarships(entities.filterIsInstance<StarshipEntity>())
            })
        joinAll(peopleJob, filmsJob, vehiclesJob, speciesJob, starshipsJob)
        Log.d(TAG, "All jobs are done!")
        storeCrossReferences()
    }

    private suspend fun storeCrossReferences() {
        val personFilmsRefs = mutableListOf<PersonFilmsCrossRef>()
        val personSpecieRefs = mutableListOf<PersonSpecieCrossRef>()
        val personVehicleRefs = mutableListOf<PersonVehicleCrossRef>()
        val personStarshipRefs = mutableListOf<PersonStarshipCrossRef>()
        val people = storageSource.getPeople().first()
        people.forEach { personEntity ->
            personEntity.filmsIds?.map { filmId ->
                personFilmsRefs.add(PersonFilmsCrossRef(personId = personEntity.personId, filmId))
            }
            personEntity.speciesIds?.map { specieId ->
                personSpecieRefs.add(
                    PersonSpecieCrossRef(
                        personId = personEntity.personId,
                        specieId
                    )
                )
            }
            personEntity.vehiclesIds?.map { vehicleId ->
                personVehicleRefs.add(
                    PersonVehicleCrossRef(
                        personId = personEntity.personId,
                        vehicleId
                    )
                )
            }
            personEntity.starshipsIds?.map { starshipId ->
                personStarshipRefs.add(
                    PersonStarshipCrossRef(
                        personId = personEntity.personId,
                        starshipId
                    )
                )
            }
        }
        storageSource.storePeopleFilmsRef(personFilmsRefs)
        storageSource.storePeopleSpecieRef(personSpecieRefs)
        storageSource.storePeopleVehicleRef(personVehicleRefs)
        storageSource.storePeopleStarshipRef(personStarshipRefs)
    }


    private suspend fun <T : EntityConvertible> fetchEntities(
        networkSourceCallable: suspend (Int) -> Result<PagedResponse<T>>,
        storageSourceFunction: suspend (List<StorageEntity>) -> Unit
    ): Job {
        val context = coroutineContext + Dispatchers.IO
        val scope = CoroutineScope(context)
        return scope.launch {
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
