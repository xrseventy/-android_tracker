package com.example.tracker.presenter

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
        ModelWalksScreenState("", "", model.getListWalksFromModel(), false)

    override fun init() {
        loadListWalk()
        trackerView.renderView(modelWalksScreenState)
    }

    override fun clickAddButton() {
        updateModelWalks(areErrorVisible = false)
        if (modelWalksScreenState.isValidFields) {
            addToList()
            saveListWalksToSharedPref()
            prepareScreen()
            trackerView.renderView(modelWalksScreenState)
        } else
        {   updateModelWalks(areErrorVisible = true)
            trackerView.renderView(modelWalksScreenState)
        }
    }
    override fun onLocationTextChanged(inputLocation: String) =
        updateModelWalks(location = inputLocation)

    override fun onDistanceTextChanged(inputDistance: String) =
        updateModelWalks(distance = inputDistance)

    private fun loadListWalk() {
        if (isWalkListInSharedPref()) {
            getSavedWalksListFromSharedPref()
        }
    }

    private fun addToList() {
        val savedWalksClass = SavedWalk(
            modelWalksScreenState.enterLocation,
            modelWalksScreenState.enterDistance.toDouble()
        )
        val newList = modelWalksScreenState.listWalks.toMutableList()
        newList.add(savedWalksClass)
        updateModelWalks(listWalks = newList)
        model.addWalk(savedWalksClass)
    }

    private fun saveListWalksToSharedPref() {
        val listWalksToJson = Gson().toJson(modelWalksScreenState.listWalks)
        model.saveSharedPref(
            listWalksToJson
        )
    }

    private fun prepareScreen() {
        trackerView.clearEditTexts()
        trackerView.closeKeyboards()
    }

    private fun updateModelWalks(
        location: String = modelWalksScreenState.enterLocation,
        distance: String = modelWalksScreenState.enterDistance,
        listWalks: List<SavedWalk> = modelWalksScreenState.listWalks,
        areErrorVisible: Boolean = modelWalksScreenState.areErrorsVisible
    ) {
        modelWalksScreenState = ModelWalksScreenState(location, distance, listWalks, areErrorVisible)
    }

    private fun isWalkListInSharedPref(): Boolean {
        return model.checkKeySavedWalksList()
    }

    private fun getSavedWalksListFromSharedPref(): List<SavedWalk> {
        val type: Type = object : TypeToken<List<SavedWalk?>?>() {}.type
        val loadedList = Gson().fromJson<List<SavedWalk>>(model.getListWalksFromSharedPref(), type)
        updateModelWalks(listWalks = loadedList)
        return loadedList
    }
}