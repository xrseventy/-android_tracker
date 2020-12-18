package com.example.tracker.data

import android.content.Context

class PreferencesProvider(context: Context) {

    private val sharedPreferences =
        context.getSharedPreferences(KEY_STR_NAME_FOLDER, Context.MODE_PRIVATE)
     val editor = sharedPreferences.edit()


    fun putString(key: String, value: String) {
        editor.putString(key, value).apply()
    }

    fun getString(key: String): String? =
        sharedPreferences.getString(key, null)

    fun hasKey(key: String): Boolean {
        return sharedPreferences.contains(key)
    }

    fun clearList() {
        sharedPreferences.edit().remove(KEY_STR_SAVED_WALK).apply()
    }

    companion object {
        const val KEY_STR_SAVED_WALK = "SavedWalk"
        const val KEY_STR_NAME_FOLDER = "sharedPrefFile"
    }

}
