package com.smartwalkietalkie.gantry

import android.app.Application
import timber.log.Timber

class GuardApp : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        Timber.d("onCreate...")
    }
}