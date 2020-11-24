package com.example.tracker.view

import com.example.tracker.common.SavedWalk

interface SavedWalksView {

    fun updateAdapter(listWalks: List<SavedWalk>)
    fun updateProgress(idResString: Int, countProgress: Int)
    fun saveSharedPref(savedLocation: String, savedDistance: Int)
    fun closeKeyboards()
    fun updateProgressBar(values: Int)
    fun setErrorDistance(errorTextResId: Int)
    fun setErrorLocation(errorTextResId: Int)
    fun clearEditTexts()
    fun makeToast()
}