package com.android_academy.custompagination

import android.app.Application
import android.content.Context

class PagingApp : Application() {

    override fun onCreate() {
        super.onCreate()
        application = this
    }

    companion object {
        var application: Context? = null

        fun getAppContext(): Context {
            return application!!
        }
    }
}