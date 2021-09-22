package com.android_academy.custompagination

import android.app.Application
import com.android_academy.custompagination.di.AppComponent
import com.android_academy.custompagination.di.AppModule
import com.android_academy.custompagination.di.DaggerAppComponent

class StarWarsApp : Application() {

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }

    companion object {
        private var appComponent: AppComponent? = null

        fun getAppComponent(): AppComponent {
            if (appComponent == null) throw RuntimeException("AppComponent wasn't initialized")
            return appComponent!!
        }
    }
}