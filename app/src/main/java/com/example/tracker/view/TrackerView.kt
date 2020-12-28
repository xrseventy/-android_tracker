package com.example.tracker.view

import com.example.tracker.data.model.ModelWalksScreenState

interface TrackerView {
    fun renderView(model :ModelWalksScreenState)
    fun renderFragment(model: ModelWalksScreenState)
    fun closeKeyboards()
    fun clearEditTexts()
    //TODO del toast
    fun toast()
    fun showCongrutilationsFragment(switcher: Boolean)
}