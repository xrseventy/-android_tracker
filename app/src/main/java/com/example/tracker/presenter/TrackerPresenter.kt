package com.example.tracker.presenter

import android.content.Context
import android.util.Log
import android.view.Gravity
import android.widget.Toast
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
        ModelWalksScreenState("", "", model.getListWalksFromModel(), areErrorsVisible = false, congrulationVisible = false)

    override fun init() {
       // model.clearSavedWalksList()
        loadListWalk()
        trackerView.renderView(modelWalksScreenState)
        Log.d(this.toString(), "num ${modelWalksScreenState.num}")
    }

    override fun clickAddButton() {
        updateModelWalks(areErrorVisible = false)
        if (modelWalksScreenState.isValidFields) {
            checkBestDistance()
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
        areErrorVisible: Boolean = modelWalksScreenState.areErrorsVisible,
        congrulationVisible: Boolean = modelWalksScreenState.congrulationVisible
    ) {
        modelWalksScreenState = ModelWalksScreenState(location, distance, listWalks, areErrorVisible, congrulationVisible)
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

    private fun findBiggestDistance(){
        //if (modelWalksScreenState.enterDistance.toDouble() > modelWalksScreenState.isBiggestDistance)
        Log.d(this.toString(), "num ${modelWalksScreenState.num}")
    }

    //TODO del toast
    private fun  checkBestDistance() {
        if (modelWalksScreenState.enterDistance.toDouble() > modelWalksScreenState.num!!) {
            updateModelWalks(congrulationVisible = true)
            trackerView.toast()
        }
    }
}