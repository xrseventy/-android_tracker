package com.example.tracker.data.model

import com.example.tracker.data.SavedWalk

class ModelWalksScreenState(
    val enterLocation: String,
    val enterDistance: String,
    val listWalks: List<SavedWalk>

) {
    val isEnterDistanceValid = enterDistance.isNotEmpty()
    val isEnterLocationValid = enterLocation.isNotEmpty()
    val isValidFields = (isEnterDistanceValid && isEnterLocationValid)
    val totalDistance = listWalks.fold(0) { acc, value -> acc + value.distance }
}
