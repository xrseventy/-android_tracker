package com.example.tracker.presenter

import com.example.tracker.R
import com.example.tracker.common.App
import com.example.tracker.model.ModelWalksScreenState
import com.example.tracker.common.SavedWalk
import com.example.tracker.view.SavedWalksView

class SavedWalksPresenterImpl(
    private val iMainView: SavedWalksView, private val model: App.Model

) : SavedWalksPresenter {

    private var modelWalksScreenState: ModelWalksScreenState =
        ModelWalksScreenState("", "", model.getListWalk())

    fun init() {
        setProgressText()
        iMainView.updateAdapter(modelWalksScreenState.listWalks)
    }

    override fun clickAddButton() {
        if (modelWalksScreenState.isValidFields) {
            updateScreen()
            setProgressData()
            iMainView.updateAdapter(modelWalksScreenState.listWalks)
            iMainView.closeKeyboards()
        } else
            setError()
    }

    override fun onLocationTextChanged(s: String) {
        updateModelWalks(location = s)
    }

    override fun onDistanceTextChanged(s: String) {
        updateModelWalks(distance = s)
    }


    private fun updateModelWalks(
        location: String = modelWalksScreenState.enterLocation,
        distance: String = modelWalksScreenState.enterDistance,
        listWalks: List<SavedWalk> = modelWalksScreenState.listWalks
    ) {
        modelWalksScreenState = ModelWalksScreenState(location, distance, listWalks)
    }

    private fun updateScreen() {
        saveWalks()
        updateAnimationProgressBar()
        iMainView.clearEditTexts()
        iMainView.makeToast()
    }

    private fun setProgressData() {
        updateAnimationProgressBar()
        setProgressText()
    }

    private fun saveWalks() {
        addToList()
        iMainView.saveSharedPref(
            modelWalksScreenState.enterLocation,
            modelWalksScreenState.enterDistance.toInt()
        )
    }

    private fun addToList() {
        val savedWalksClass = SavedWalk(
            modelWalksScreenState.enterLocation,
            modelWalksScreenState.enterDistance.toInt()
        )
        val screenStateList = modelWalksScreenState.listWalks + mutableListOf(savedWalksClass)
        updateModelWalks(listWalks = screenStateList)
        model.addWalk(savedWalksClass)
    }

    private fun updateAnimationProgressBar() {
        iMainView.updateProgressBar(modelWalksScreenState.currentDistance)
    }

    private fun setProgressText() {
        val numProgress = modelWalksScreenState.currentDistance
        val textIdProgress = R.string.your_progress
        iMainView.updateProgress(textIdProgress, numProgress)
    }

    private fun setError() {
        val textIdDistance = R.string.ErrorDistance
        val textIdLocation = R.string.ErrorLocation
        if (!(modelWalksScreenState.isEnterDistanceValid))
            iMainView.setErrorDistance(textIdDistance)
        if ((!modelWalksScreenState.isEnterLocationValid))
            iMainView.setErrorLocation(textIdLocation)
    }
}