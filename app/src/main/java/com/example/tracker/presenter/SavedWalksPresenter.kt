package com.example.tracker.presenter

interface SavedWalksPresenter {

    fun clickAddButton()
    fun onLocationTextChanged(inputLocation: String)
    fun onDistanceTextChanged(inputDistance: String)
}

