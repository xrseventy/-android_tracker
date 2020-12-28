package com.example.tracker.presenter

interface Presenter {

    fun init()
    fun clickAddButton()
//    fun clickWowButton()
    fun onLocationTextChanged(inputLocation: String)
    fun onDistanceTextChanged(inputDistance: String)
}

