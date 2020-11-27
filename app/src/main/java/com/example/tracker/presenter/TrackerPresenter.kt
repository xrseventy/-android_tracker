package com.example.tracker.presenter

import android.util.Log
import com.example.tracker.R
import com.example.tracker.data.model.Model
import com.example.tracker.data.SavedWalk
import com.example.tracker.data.model.ModelWalksScreenState
import com.example.tracker.view.TrackerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class TrackerPresenter(
    private val trackerView: TrackerView, private val model: Model

) : Presenter {

    private var modelWalksScreenState: ModelWalksScreenState =
        ModelWalksScreenState("", "", model.getListWalk())

    override fun init() {

        trackerView.setEditText(model.setTextLocation(), model.setTextDistance())
        loadListWalk()
        //model.clearSavedWalksList()
        setProgressBar()
        trackerView.updateAdapter(modelWalksScreenState.listWalks)
    }

    override fun clickAddButton() {
        if (modelWalksScreenState.isValidFields) {
            addToList()
            trackerView.setFirstLaunchMessage(false)
            saveListWalksToSharedPref()
            setProgressBar()
            prepareScreen()
            trackerView.updateAdapter(modelWalksScreenState.listWalks)
        } else
            setError()
    }

    private fun loadListWalk() {

        if (isWalkListInSharedPref()) {
            getSavedWalksListFromSharedPref()
        } else {
            Log.d(this.toString(), "HERE")
            trackerView.setFirstLaunchMessage(true)
        }
    }

    private fun addToList() {
        val savedWalksClass = SavedWalk(
            modelWalksScreenState.enterLocation,
            modelWalksScreenState.enterDistance.toInt()
        )
        val newList = modelWalksScreenState.listWalks.toMutableList()
        newList.add(savedWalksClass)
        updateModelWalks(listWalks = newList)
        model.addWalk(savedWalksClass)
    }

    private fun saveListWalksToSharedPref() {
        val listWalksToJson = Gson().toJson(modelWalksScreenState.listWalks)
        model.saveSharedPref(
            modelWalksScreenState.enterLocation,
            modelWalksScreenState.enterDistance.toInt(),
            listWalksToJson
        )
    }

    private fun prepareScreen() {
        trackerView.clearEditTexts()
        trackerView.makeToast(R.string.toastAdd)
        trackerView.closeKeyboards()
    }

    private fun setProgressBar() {
        updateAnimationProgressBar()
        setProgressText()
    }

    private fun updateModelWalks(
        location: String = modelWalksScreenState.enterLocation,
        distance: String = modelWalksScreenState.enterDistance,
        listWalks: List<SavedWalk> = modelWalksScreenState.listWalks
    ) {
        modelWalksScreenState = ModelWalksScreenState(location, distance, listWalks)
    }

    private fun isWalkListInSharedPref(): Boolean {
        return model.checkKeySavedWalksList()
    }

    private fun getSavedWalksListFromSharedPref(): List<SavedWalk> {
        val type: Type = object : TypeToken<List<SavedWalk?>?>() {}.type
        val loadList = Gson().fromJson<List<SavedWalk>>(model.setListWalks(), type)
        updateModelWalks(listWalks = loadList)
        return loadList
    }

    private fun updateAnimationProgressBar() {
        trackerView.updateProgressBar(modelWalksScreenState.totalDistance)
    }

    private fun setProgressText() {
        val numProgress = modelWalksScreenState.totalDistance
        val textIdProgress = R.string.your_progress
        trackerView.updateProgress(textIdProgress, numProgress)
    }

    private fun setError() {
        val textIdDistance = R.string.errorDistance
        val textIdLocation = R.string.errorLocation
        if (!modelWalksScreenState.isEnterDistanceValid) {
            trackerView.setErrorDistance(textIdDistance)
        }
        if (!modelWalksScreenState.isEnterLocationValid) {
            trackerView.setErrorLocation(textIdLocation)
        }
    }

    override fun onLocationTextChanged(inputLocation: String) =
        updateModelWalks(location = inputLocation)

    override fun onDistanceTextChanged(inputDistance: String) =
        updateModelWalks(distance = inputDistance)
}