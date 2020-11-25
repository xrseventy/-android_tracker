package com.example.tracker.model

import com.example.tracker.common.SavedWalk

class ModelWalksScreenState(
    val enterLocation: String,
    val enterDistance: String,
    val listWalks: List<SavedWalk>

) {
    val isEnterDistanceValid = enterDistance.isNotEmpty()
    val isEnterLocationValid = enterLocation.isNotEmpty()
    val isValidFields = (isEnterDistanceValid && isEnterLocationValid)
    val currentDistance = listWalks.fold(0) { acc, value -> acc + value.distance }
}
