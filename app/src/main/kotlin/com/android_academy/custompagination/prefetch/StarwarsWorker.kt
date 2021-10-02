package com.android_academy.custompagination.prefetch

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.android_academy.custompagination.StarWarsApp
import com.android_academy.custompagination.repo.StarWarsRepo
import javax.inject.Inject

class StarWarsWorker(appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams) {

    @Inject
    lateinit var repo: StarWarsRepo

    override suspend fun doWork(): Result {
        Log.d(TAG, "doWork: called")
        StarWarsApp.getAppComponent().inject(this)
        repo.fetchData()
        return Result.success()
    }

    companion object {
        const val TAG = "StarWarsWorker"
        const val STAR_WARS_PREFETCH_WORKER = "star_wars_prefetch_worker"
    }
}