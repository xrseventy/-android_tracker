package com.example.tracker.data

import android.app.Application
import com.example.tracker.data.model.Model
import com.google.gson.Gson

class App : Application() {

    lateinit var model: Model

    override fun onCreate() {
        super.onCreate()
        val preferencesProvider = PreferencesProvider(this)
        val gson: Gson =  Gson()
        model = Model(preferencesProvider, gson)
    }

}
