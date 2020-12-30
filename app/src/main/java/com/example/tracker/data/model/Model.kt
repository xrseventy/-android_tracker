package com.example.tracker.data.model

import com.example.tracker.data.PreferencesProvider
import com.example.tracker.data.SavedWalk
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class Model(private val preferencesProvider: PreferencesProvider, private val gson: Gson) {
    private val listWalks: MutableList<SavedWalk> = mutableListOf()

    fun addWalk(walk: SavedWalk) {
        listWalks.add(walk)
    }

    fun getListWalksFromModel(): List<SavedWalk> {
        return listWalks
    }

    fun saveSharedPref(savedListWalks: String) {
        preferencesProvider.putString(PreferencesProvider.KEY_STR_SAVED_WALK, savedListWalks)
    }

    fun getListWalksFromSharedPref(): String? {
        return (preferencesProvider.getString(PreferencesProvider.KEY_STR_SAVED_WALK))
    }

    fun checkKeySavedWalksList(): Boolean {
        return preferencesProvider.hasKey(PreferencesProvider.KEY_STR_SAVED_WALK)
    }
    //TODO del
    fun clearSavedWalksList(){
        return preferencesProvider.clearList()
    }

    //TODO rename
    fun getSavedWalksListFromSharedPref(): List<SavedWalk> {
        val type: Type = object : TypeToken<List<SavedWalk?>?>() {}.type
        return gson.fromJson(getListWalksFromSharedPref(), type)
    }

}