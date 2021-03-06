package com.android_academy.offline_first_app.prefetch

import com.android_academy.offline_first_app.StarWarsApp
import com.android_academy.offline_first_app.repo.StarWarsRepo
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