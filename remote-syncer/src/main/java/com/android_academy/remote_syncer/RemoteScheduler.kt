package com.android_academy.remote_syncer

import android.content.Context
import android.util.Log
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.WorkRequest
import androidx.work.WorkerParameters
import com.android_academy.remote_syncer.RemoteSchedulerImpl.Companion.ID_WORKER_INPUT_KEY
import com.android_academy.remote_syncer.models.RemoteData
import com.android_academy.remote_syncer.models.RemoteDataId
import com.android_academy.remote_syncer.models.SyncStatus
import java.util.concurrent.TimeUnit
import javax.inject.Inject

interface RemoteScheduler {
    suspend fun schedule(remoteData: RemoteData)
}

class RemoteSchedulerImpl<T : RemoteWorker>(
    private val workManager: WorkManager,
    private val workerClass: Class<T>,
    private val workName: String,
    private val persistentSource: RemotePersistenceSource
) : RemoteScheduler {
    override suspend fun schedule(remoteData: RemoteData) {
        Log.d(TAG, "schedule(): Scheduling remote data. Id - ${remoteData.id}")

        val request = OneTimeWorkRequest.Builder(workerClass)
            .setBackoffCriteria(
                BackoffPolicy.LINEAR,
                WorkRequest.MIN_BACKOFF_MILLIS,
                TimeUnit.MILLISECONDS
            )
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .setInputData(
                Data.Builder()
                    .putLong(ID_WORKER_INPUT_KEY, remoteData.id)
                    .build()
            )
            .build()

        workManager
            .enqueueUniqueWork(
                workName,
                ExistingWorkPolicy.APPEND_OR_REPLACE,
                request
            )

        persistentSource.update(remoteData.copy(syncStatus = SyncStatus.PENDING))
    }

    companion object {
        const val TAG = "RemoteSchedulerImpl"
        const val ID_WORKER_INPUT_KEY = "ID_WORKER_INPUT_KEY"
    }

}

class SpecificRemoteWorker(
    private val appContext: Context,
    workerParams: WorkerParameters
) : RemoteWorker(appContext, workerParams) {

    @Inject
    internal lateinit var syncService: SyncService

    override fun provideSyncService(): SyncService {
        (appContext as RemoteInjectProvider).inject(this)
        return syncService
    }
}

abstract class RemoteWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    abstract fun provideSyncService(): SyncService

    final override suspend fun doWork(): Result {
        return sync(provideSyncService())
    }

    private suspend fun sync(syncService: SyncService): Result {
        Log.d(TAG, "[RemoteWorker], sync()")
        val id = extractRemoteDataId() ?: return Result.success()
        return syncService.sync(id, runAttemptCount)
    }

    private fun extractRemoteDataId(): RemoteDataId? {
        return inputData.getLong(ID_WORKER_INPUT_KEY, -1)
            .takeIf { it != -1L }
            ?: run {
                Log.e(
                    TAG,
                    "sync failed: Couldn't get id from input data",
                    RemoteWorkerThrowable("Couldn't get id from input data"),
                )
                null
            }
    }


    companion object {
        const val TAG = "RemoteWorker"
    }
}

class RemoteWorkerThrowable(override val message: String?) : Throwable()

