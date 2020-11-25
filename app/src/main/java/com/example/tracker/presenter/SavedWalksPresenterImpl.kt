package com.example.tracker.presenter

import android.util.Log
import com.example.tracker.R
import com.example.tracker.common.Model
import com.example.tracker.common.SavedWalk
import com.example.tracker.model.ModelWalksScreenState
import com.example.tracker.view.SavedWalksView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class SavedWalksPresenterImpl(
    private val iMainView: SavedWalksView, private val model: Model

) : SavedWalksPresenter {

    private var modelWalksScreenState: ModelWalksScreenState =
        ModelWalksScreenState("", "", model.getListWalk())

    fun init() {

        model.setTextLocation()
            ?.let { model.setTextDistance()?.let { it1 -> iMainView.setEditText(it, it1) } }
        loadListWalk()
        setProgressBar()
        iMainView.updateAdapter(modelWalksScreenState.listWalks)
    }


    override fun clickAddButton() {
        if (modelWalksScreenState.isValidFields) {
            addToList()
            saveListWalksToSharedPref()
            setProgressBar()
            prepareScreen()
            iMainView.updateAdapter(modelWalksScreenState.listWalks)
           //model.clearSavedWalksList()
        } else
            setError()
    }
    private fun loadListWalk(){
        if(isWalkListInSharedPref()) {
            getSavedWalksListFromSharedPref()
        }
        else {
            Log.d(this.toString(), "HERE")
            iMainView.makeToast(R.string.toastFirstWalk) //TODO for your first walk

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
        iMainView.clearEditTexts()
        iMainView.makeToast(R.string.toastAdd) //TODO for added elem
        iMainView.closeKeyboards()
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

    private fun isWalkListInSharedPref():Boolean{
        return model.checkKeySavedWalksList()
    }

    private fun getSavedWalksListFromSharedPref(): List<SavedWalk> {
       // val string: String? = model.setListWalks()
        val type: Type = object : TypeToken<List<SavedWalk?>?>() {}.type
        val loadList = Gson().fromJson<List<SavedWalk>>(model.setListWalks(), type)
        //model.

//        Log.d(this.toString(), "loadList =$loadList")
//        Log.d(this.toString(), "string =$string")
       // if(model.checkKeySavedWalksList())

            updateModelWalks(listWalks = loadList)
       // else {
         //   Log.d(this.toString(), "HERE")
         //   iMainView.makeToast(R.string.toastFirstWalk) //TODO for your first walk

      //  }
        return loadList
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
        val textIdDistance = R.string.errorDistance
        val textIdLocation = R.string.errorLocation
        if (!(modelWalksScreenState.isEnterDistanceValid))
            iMainView.setErrorDistance(textIdDistance)
        if ((!modelWalksScreenState.isEnterLocationValid))
            iMainView.setErrorLocation(textIdLocation)
    }

    override fun onLocationTextChanged(s: String) {
        updateModelWalks(location = s)
    }

    override fun onDistanceTextChanged(s: String) {
        updateModelWalks(distance = s)
    }

}