package com.android_academy.custompagination.network

import com.android_academy.custompagination.StarWarsApp
import com.android_academy.custompagination.repo.StarWarsRepo
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

class PushMessagingService : FirebaseMessagingService() {

    @Inject
    lateinit var repo: StarWarsRepo

    private val scope by lazy { CoroutineScope(SupervisorJob() + Dispatchers.Default) }

    override fun onCreate() {
        StarWarsApp.getAppComponent().inject(this)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        if (message.messageType == StarWarsRepo.REFRESH_DATA_MSG_TYPE) {
            scope.launch {
                repo.fetchData()
            }
        }
    }
}