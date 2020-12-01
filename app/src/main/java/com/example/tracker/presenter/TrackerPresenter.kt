package com.example.tracker.presenter

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

        loadListWalk()
        trackerView.renderView(modelWalksScreenState)
    }

    override fun clickAddButton() {
        modelWalksScreenState.addFunction = true
        if (modelWalksScreenState.isValidFields) {
            addToList()
            saveListWalksToSharedPref()
            prepareScreen()
            trackerView.renderView(modelWalksScreenState)
        } else
            trackerView.renderView(modelWalksScreenState)
    }

    private fun loadListWalk() {

        if (isWalkListInSharedPref()) {
            getSavedWalksListFromSharedPref()
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
        val loadList = Gson().fromJson<List<SavedWalk>>(model.getListWalks(), type)
        updateModelWalks(listWalks = loadList)
        return loadList
    }

    override fun onLocationTextChanged(inputLocation: String) =
        updateModelWalks(location = inputLocation)

    override fun onDistanceTextChanged(inputDistance: String) =
        updateModelWalks(distance = inputDistance)
}