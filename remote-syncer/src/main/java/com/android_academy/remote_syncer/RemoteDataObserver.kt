package com.android_academy.remote_syncer

import android.util.Log
import com.android_academy.network.NetworkThrowable
import com.android_academy.remote_syncer.models.RemoteData
import com.android_academy.remote_syncer.models.RemoteResponse
import com.android_academy.remote_syncer.models.SyncStatus
import com.android_academy.remote_syncer.operations.RemoteOperation
import com.android_academy.remote_syncer.provider.RemoteOperationMapper
import com.android_academy.remote_syncer.provider.RemoteOperationProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

interface RemoteDataObserver {
    fun startObserving()
    fun stopObserving()
}

class RemoteDataObserverImpl(
    private val persistenceSource: RemotePersistenceSource,
    private val mapper: RemoteOperationMapper,
    private val remoteScheduler: RemoteScheduler
) : RemoteDataObserver {

    private var observingJov: Job? = null

    override fun startObserving() {
        val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())
        observingJov = scope.launch {
            persistenceSource.observeData().map { remoteDataList ->
                filterNewRemoteDataOnly(remoteDataList)
            }.collect { newRemoteDataList ->
                Log.d(TAG, "startObserving: Received changes. size: ${newRemoteDataList.size}")
                handleNewRemoteDataList(newRemoteDataList)
            }
        }
    }

    override fun stopObserving() {
        Log.d(TAG, "stopObserving")
        observingJov?.cancel()
    }

    private suspend fun handleNewRemoteDataList(remoteDataList: List<RemoteData>) {
        remoteDataList.forEach { remoteData ->
            if (shouldExecuteRemoteData()) {
                Log.d(TAG, "handleNewRemoteDataList: executing remote data. id - ${remoteData.id}")
                executeRemoteData(remoteData)
            } else {
                Log.d(TAG, "handleNewRemoteDataList: enqueuing remote data. id - ${remoteData.id}")
                scheduleOnQueueNotEmpty(remoteData)
            }
        }
    }

    private suspend fun scheduleOnQueueNotEmpty(remoteData: RemoteData) {
        remoteScheduler.schedule(remoteData)
    }

    private suspend fun executeRemoteData(remoteData: RemoteData) {
        val provider = mapper[remoteData.type] ?: return
        val operation: RemoteOperation =
            provider.provideParser().fromJson(remoteData.data) ?: return

        val result = provider.providerOperationHandler().execute(operation)

        when {
            result.isSuccess -> {
                val value = result.getOrNull() ?: return
                handleSuccess(value, remoteData, operation, provider)
            }
            result.isFailure -> {
                handleFailure(result.exceptionOrNull(), remoteData, operation, provider)
            }
        }
    }

    private suspend fun handleFailure(
        throwable: Throwable?,
        remoteData: RemoteData,
        operation: RemoteOperation,
        provider: RemoteOperationProvider<*, *>
    ) {
        Log.e(TAG, "[RemoteDataObserver], sync failure", throwable)

//        val shouldAddToQueue = shouldAddToQueue(throwable)

//        if (shouldAddToQueue) {
//            Log.d(TAG, "[RemoteDataObserver], handleFailure(): Adding operation to queue")
//
//        } else {
//            Log.e(
//                TAG,
//                "handleFailure: Remote operation failed. " +
//                        "operation_type ${remoteData.type.getName()}," +
//                        " data ${remoteData.data}," +
//                        " stack_trace: ${throwable?.stackTraceToString()}"
//            )
//            provider.provideFailureHandler().executeOnFailure(operation, throwable)
//            persistenceSource.remove(remoteData)
//        }

        remoteScheduler.schedule(remoteData)
    }

    private fun shouldAddToQueue(throwable: Throwable?): Boolean {
        return when {
            throwable !is NetworkThrowable -> false
            throwable.errorCode != null -> false
            else -> true
        }
    }

    private suspend fun handleSuccess(
        response: RemoteResponse,
        remoteData: RemoteData,
        operation: RemoteOperation,
        provider: RemoteOperationProvider<*, *>
    ) {
        Log.d(TAG, "[RemoteDataObserver], executeRemoteData(): sync successful")
        provider.provideSuccessHandler().executeOnSuccess(
            data = operation,
            response = response
        )
        persistenceSource.remove(remoteData)
    }


    private suspend fun shouldExecuteRemoteData(): Boolean {
        return persistenceSource.isQueueIsEmpty()
    }


    private fun filterNewRemoteDataOnly(list: List<RemoteData>): List<RemoteData> {
        return list.filter { remoteData ->
            remoteData.syncStatus == SyncStatus.NEW
        }
    }

    companion object {
        const val TAG = "RemoteDataObserverImpl"
    }
}

