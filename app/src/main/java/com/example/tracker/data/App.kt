package com.example.tracker.data

import android.app.Application
import com.example.tracker.data.model.Model

class App : Application() {

    lateinit var model: Model

    override fun onCreate() {
        super.onCreate()
        val preferencesProvider = PreferencesProvider(this)
        model = Model(preferencesProvider)
    }

}
