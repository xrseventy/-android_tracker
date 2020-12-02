package com.example.tracker.data.model

import androidx.annotation.StringRes
import com.example.tracker.R
import com.example.tracker.data.SavedWalk
import com.example.tracker.view.TrackerView

class ModelWalksScreenState(
    val enterLocation: String,
    val enterDistance: String,
    val listWalks: List<SavedWalk>,
    val areErrorsVisible: Boolean

) {
    val isEnterDistanceValid = enterDistance.isNotEmpty()
    val isEnterLocationValid = enterLocation.isNotEmpty()
    val isValidFields = (isEnterDistanceValid && isEnterLocationValid)
    val totalDistance = listWalks.fold(0) { acc, value -> acc + value.distance }
    val isFirstLaunchMessageVisible = listWalks.isEmpty()

    @StringRes
    val distanceErrorResId: Int? = if (!isEnterDistanceValid) {
        R.string.errorDistance
    } else {
        null
    }

    @StringRes
    val locationErrorResId: Int? = if (!isEnterLocationValid) {
        R.string.errorLocation
    } else {
        null
    }

}
