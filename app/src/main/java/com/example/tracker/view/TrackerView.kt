package com.example.tracker.view

import com.example.tracker.data.model.ModelWalksScreenState

interface TrackerView {
    fun renderView(model :ModelWalksScreenState)
    fun closeKeyboards()
    fun clearEditTexts()
}