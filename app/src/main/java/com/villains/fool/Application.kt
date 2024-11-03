package com.villains.fool

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Application: Application() {
    override fun onCreate() {
        super.onCreate()

        prefs = Prefs(this@Application)
    }

    companion object {
        const val TAG = "EXCHAGER"

        lateinit var prefs: Prefs
    }
}