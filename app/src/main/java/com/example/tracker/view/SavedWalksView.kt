package com.example.tracker.view

import androidx.annotation.StringRes
import com.example.tracker.common.SavedWalk

interface SavedWalksView {

    fun updateAdapter(listWalks: List<SavedWalk>)
    fun updateProgress(idResString: Int, countProgress: Int)
 //   fun saveSharedPref(savedLocation: String, savedDistance: Int)
// fun setEditText()
    fun setEditText(location :String, distance :Int)
    fun closeKeyboards()
    fun updateProgressBar(values: Int)
    fun setErrorDistance(@StringRes errorTextResId: Int)
    fun setErrorLocation(@StringRes errorTextResId: Int)
    fun clearEditTexts()
    fun makeToast(@StringRes textForToastResId: Int)
}