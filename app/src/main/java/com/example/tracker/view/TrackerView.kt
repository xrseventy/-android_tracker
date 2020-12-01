package com.example.tracker.view

import androidx.annotation.StringRes
import com.example.tracker.data.SavedWalk
import com.example.tracker.data.model.ModelWalksScreenState

interface TrackerView {

    fun renderView(model :ModelWalksScreenState)
    fun closeKeyboards()
    fun clearEditTexts()
    fun makeToast(@StringRes textForToastResId: Int)
}