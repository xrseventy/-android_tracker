package com.example.tracker.common

import android.app.Application

class App : Application() {

    lateinit var model: Model

    override fun onCreate() {
        super.onCreate()
        val preferencesProvider = PreferencesProvider(this)
        model = Model(preferencesProvider)
    }

}
