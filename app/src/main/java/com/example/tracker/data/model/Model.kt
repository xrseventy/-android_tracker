package com.example.tracker.data.model

import com.example.tracker.data.PreferencesProvider
import com.example.tracker.data.SavedWalk

class Model(private val preferencesProvider: PreferencesProvider) {
    private val listWalks: MutableList<SavedWalk> = mutableListOf()

    fun addWalk(walk: SavedWalk) {
        listWalks.add(walk)
    }

    fun getListWalk(): List<SavedWalk> {
        return listWalks
    }

    fun saveSharedPref(savedLocation: String, savedDistance: Int, savedListWalks: String) {
        preferencesProvider.putInt(PreferencesProvider.KEY_INT_DISTANCE, savedDistance)
        preferencesProvider.putString(PreferencesProvider.KEY_STR_LOCATION, savedLocation)
        preferencesProvider.putString(PreferencesProvider.KEY_STR_SAVED_WALK, savedListWalks)
    }

    fun getTextLocation(): String? {
        return (preferencesProvider.getString(PreferencesProvider.KEY_STR_LOCATION))
    }

    fun getTextDistance(): Int? {
        return (preferencesProvider.getInt(PreferencesProvider.KEY_INT_DISTANCE))
    }

    fun getListWalks(): String? {
        return (preferencesProvider.getString(PreferencesProvider.KEY_STR_SAVED_WALK))
    }

    fun checkKeySavedWalksList(): Boolean {
        return preferencesProvider.hasKey(PreferencesProvider.KEY_STR_SAVED_WALK)
    }

}