package com.android_academy.remote_syncer

import android.util.Log
import androidx.work.ListenableWorker
import com.android_academy.remote_syncer.SyncService.Companion.MAX_ATTEMPTS
import com.android_academy.remote_syncer.models.RemoteData
import com.android_academy.remote_syncer.models.RemoteDataId
import com.android_academy.remote_syncer.models.RemoteResponse
import com.android_academy.remote_syncer.models.SyncStatus
import com.android_academy.remote_syncer.operations.RemoteOperation
import com.android_academy.remote_syncer.provider.RemoteOperationMapper
import com.android_academy.remote_syncer.provider.RemoteOperationProvider

interface SyncService {
    suspend fun sync(id: RemoteDataId, runAttemptCount: Int): ListenableWorker.Result

    companion object {
        const val MAX_ATTEMPTS = 3
    }
}

class SyncServiceImpl(
    private val mapper: RemoteOperationMapper,
    private val persistentSource: RemotePersistenceSource,
) : SyncService {

    override suspend fun sync(id: RemoteDataId, runAttemptCount: Int): ListenableWorker.Result {
        Log.d(TAG, "[SyncService], sync(): syncing remote data. id - $id")

        val remoteData = persistentSource.getRemoteData(id)?.copy(
            syncStatus = SyncStatus.EXECUTING
        ) ?: return onCurrentRunFailure(
            SyncServiceThrowable("Couldn't find item by id"),
            runAttemptCount
        )
        persistentSource.update(remoteData)

        val provider = mapper[remoteData.type]
            ?: return onCurrentRunFailure(
                SyncServiceThrowable("Couldn't find provider"),
                runAttemptCount
            )
        val operation =
            provider.provideParser().fromJson(remoteData.data)
                ?: return onCurrentRunFailure(
                    SyncServiceThrowable("Couldn't find provider"),
                    runAttemptCount
                )


        val result = provider.providerOperationHandler().execute(operation)

        return when {
            result.isSuccess -> {
                Log.d(TAG, "sync(): Successfully synced data!")
                onSuccess(result.getOrNull(), operation, remoteData, provider)
                ListenableWorker.Result.success()
            }
            else -> {
                onCurrentRunFailure(result.exceptionOrNull(), runAttemptCount, remoteData, provider)
            }
        }
    }

    private suspend fun onSuccess(
        remoteResponse: RemoteResponse?,
        operation: RemoteOperation,
        remoteData: RemoteData,
        provider: RemoteOperationProvider<*, *>
    ) {
        if (remoteResponse == null) return

        persistentSource.remove(remoteData)
        provider.provideSuccessHandler().executeOnSuccess(
            data = operation,
            response = remoteResponse
        )
    }

    private suspend fun onCurrentRunFailure(
        throwable: Throwable?,
        runAttemptCount: Int,
        remoteData: RemoteData? = null,
        provider: RemoteOperationProvider<*, *>? = null
    ): ListenableWorker.Result {
        val isLastAttempt = runAttemptCount >= MAX_ATTEMPTS
        Log.e(TAG, "onCurrentRunFailure", throwable)
        if(remoteData == null || provider == null) return ListenableWorker.Result.failure()

        return if (isLastAttempt) {
            onFinalFailure(throwable, remoteData, provider)
            ListenableWorker.Result.success()
        } else {
            onRetry(remoteData.copy(syncStatus = SyncStatus.PENDING_RETRY))
            ListenableWorker.Result.retry()
        }
    }

    private suspend fun onFinalFailure(
        throwable: Throwable?,
        remoteData: RemoteData,
        provider: RemoteOperationProvider<*, *>
    ) {
        Log.d(TAG,"onFinalFailure(): remote data id - ${remoteData.id}")
        val operation = provider.provideParser().fromJson(remoteData.data) ?: return

        provider.provideFailureHandler().executeOnFailure(operation, throwable)

        persistentSource.remove(remoteData)
    }

    private suspend fun onRetry(remoteData: RemoteData) {

        Log.d(TAG, "onRetry(): remote data id - ${remoteData.id}")

        //Update with retry sync status
        persistentSource.update(remoteData)
    }

    companion object {
        const val TAG = "SyncServiceImpl"
    }

}

class SyncServiceThrowable(override val message: String?) : Throwable()