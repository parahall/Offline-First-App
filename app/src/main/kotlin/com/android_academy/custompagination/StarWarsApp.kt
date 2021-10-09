package com.android_academy.custompagination

import android.app.Application
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.android_academy.custompagination.di.AppComponent
import com.android_academy.custompagination.di.AppModule
import com.android_academy.custompagination.di.DaggerAppComponent
import com.android_academy.custompagination.prefetch.StarWarsWorker
import com.android_academy.remote_syncer.RemoteDataObserver
import com.android_academy.remote_syncer.RemoteInjectProvider
import com.android_academy.remote_syncer.SpecificRemoteWorker
import java.util.concurrent.TimeUnit

class StarWarsApp : Application(), RemoteInjectProvider {

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
        scheduleWorkers()
        initRemoteDataObservation(getAppComponent().remoteObserver())
    }

    private fun initRemoteDataObservation(remoteObserver: RemoteDataObserver) {
        remoteObserver.startObserving()
    }

    private fun scheduleWorkers() {
        val starWarsWorkerRequest = PeriodicWorkRequest.Builder(
            StarWarsWorker::class.java,
            8L, TimeUnit.HOURS
        )
            .setConstraints(
                Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
            )
            .build()

        WorkManager.getInstance(applicationContext)
            .enqueueUniquePeriodicWork(
                StarWarsWorker.STAR_WARS_PREFETCH_WORKER,
                ExistingPeriodicWorkPolicy.KEEP,
                starWarsWorkerRequest
            )
    }

    companion object {
        private var appComponent: AppComponent? = null

        fun getAppComponent(): AppComponent {
            if (appComponent == null) throw RuntimeException("AppComponent wasn't initialized")
            return appComponent!!
        }
    }

    override fun inject(specificRemoteWorker: SpecificRemoteWorker) {
        appComponent?.inject(specificRemoteWorker)
    }
}