package com.android_academy.custompagination.repo

import android.util.Log
import com.android_academy.custompagination.network.StarWarsApi
import com.android_academy.custompagination.storage.entities.PeopleEntity
import com.android_academy.custompagination.storage.entities.toPeopleEntity
import com.android_academy.custompagination.ui.main.StarWarsDb
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

class StarWarsRepo(private val db: StarWarsDb, private val api: StarWarsApi) {

    companion object {
        const val TAG = "StarWarsRepo"
    }

    suspend fun loadData(): Flow<List<PeopleEntity>> {
        fetchPeopleData()
        Log.d(TAG, "loadData: I'm here after fetch dispatched")

        return db.peopleDao().getAll()
    }

    private suspend fun fetchPeopleData(): Job {
        val networkExceptionHandler = CoroutineExceptionHandler { _, exception ->
            Log.d(TAG, "Load Data from network got $exception")
        }
        val context = coroutineContext + Dispatchers.IO + networkExceptionHandler
        val scope = CoroutineScope(context)

        return scope.launch {
            var hasNext = true
            var pageNumb = 1
            while (hasNext) {
                val pageResponse = api.getAllPeople(pageNumb)
                Log.d(TAG, "fetchPeopleData: people fetched. page: $pageNumb")
                if (pageResponse.isSuccessful) {
                    val page = pageResponse.body() ?: return@launch
                    hasNext = page.next?.isNotEmpty() == true
                    pageNumb++
                    val entities = page.results.map { it.toPeopleEntity() }
                    db.peopleDao().insertAll(*entities.toTypedArray())
                } else {
                    hasNext = false
                }
            }
        }
    }
}
