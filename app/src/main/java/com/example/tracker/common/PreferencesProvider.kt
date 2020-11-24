package com.example.tracker.common

import android.content.Context

class PreferencesProvider(context: Context) {

    private val sharedPreferences =
        context.getSharedPreferences("sharedPrefFile", Context.MODE_PRIVATE)

    fun putString(key: String, value: String) =
        sharedPreferences.edit().putString(key, value).apply()

    fun getString(key: String): String? =
        sharedPreferences.getString(key, null)

    fun putInt(key: String, value: Int) =
        sharedPreferences.edit().putInt(key, value).apply()

    fun getInt(key: String): Int =
        sharedPreferences.getInt(key, 0)

    companion object {
        const val KEY_LOCATION = "Location"
        const val KEY_DISTANCE = "Distance"
    }
}
