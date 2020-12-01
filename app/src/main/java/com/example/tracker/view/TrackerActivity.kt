package com.example.tracker.view

import android.animation.ObjectAnimator
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.example.tracker.data.App
import com.example.tracker.R
import com.example.tracker.data.SavedWalk
import com.example.tracker.data.model.ModelWalksScreenState
import com.example.tracker.ui.SavedWalksAdapter
import com.example.tracker.presenter.TrackerPresenter
import com.example.tracker.ui.UtilityTextWatcher
import kotlinx.android.synthetic.main.activity_fields_and_progress.*
import kotlinx.android.synthetic.main.activity_list.*


import kotlinx.android.synthetic.main.distance_field.*
import kotlinx.android.synthetic.main.location_field.*

class TrackerActivity : AppCompatActivity(), TrackerView {

    private lateinit var trackerpresenter: TrackerPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val app = (application as App)
        trackerpresenter = TrackerPresenter(this, app.model)
        addTextWatcherLocation()
        addTextWatcherDistance()
        trackerpresenter.init()
        setDistanceActionListener()
        buttonAdd.setOnClickListener {
            trackerpresenter.clickAddButton()
        }
    }

    override fun renderView(modelScreenState: ModelWalksScreenState) {

        setFirstLaunchMessage(modelScreenState.switcherForFirstLaunchMessage)
        if (!modelScreenState.switcherForFirstLaunchMessage && !lifecycle.currentState.isAtLeast(
                Lifecycle.State.STARTED
            )
        ) {
            setEditText(
                modelScreenState.listWalks.last().location,
                (modelScreenState.listWalks.last().distance)
            )
        }
        updateProgressText(modelScreenState.textIdProgress, modelScreenState.totalDistance)
        updateProgressBar(modelScreenState.totalDistance)
        updateAdapter(modelScreenState.listWalks)

        if (!modelScreenState.isValidFields && modelScreenState.addFunction) {
            if (!modelScreenState.isEnterDistanceValid)
                setErrorDistance(modelScreenState.distanceErrorResId)
            if (!modelScreenState.isEnterLocationValid)
                setErrorLocation(modelScreenState.locationErrorResId)
        }
    }

    private fun setDistanceActionListener() {
        editTextDistance.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                trackerpresenter.clickAddButton()
                true
            } else {
                false
            }
        }
    }

    override fun closeKeyboards() {
        closeKeyboard(editTextLocation)
        closeKeyboard(editTextDistance)
    }

    private fun closeKeyboard(view: View) {
        val inputMethodEditText =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodEditText.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun addTextWatcherLocation() {
        editTextLocation.addTextChangedListener(UtilityTextWatcher(trackerpresenter::onLocationTextChanged))
    }

    private fun addTextWatcherDistance() {
        editTextDistance.addTextChangedListener(UtilityTextWatcher(trackerpresenter::onDistanceTextChanged))
    }

    private fun updateAdapter(listWalks: List<SavedWalk>) {
        recyclerViewSavedAddress.adapter =
            SavedWalksAdapter(listWalks)
    }

    private fun setEditText(location: String?, distance: Int?) {
        editTextLocation.setText(location)
        editTextDistance.setText(distance.toString())
    }

    private fun updateProgressText(@StringRes idResString: Int, countProgress: Int) {
        textYourProgress.text = (getString(idResString, countProgress))
    }

    private fun updateProgressBar(values: Int) {
        ObjectAnimator.ofInt(progressBarDistance, "progress", values)
            .setDuration(200)
            .start()
    }

    private fun setErrorDistance(@StringRes errorTextResId: Int?) {
        editTextDistance.error = (errorTextResId?.let { getString(it) })
    }

    private fun setErrorLocation(@StringRes errorTextResId: Int?) {
        editTextLocation.error = (errorTextResId?.let { getString(it) })
    }

    override fun clearEditTexts() {
        editTextLocation.editableText.clear()
        editTextLocation.clearFocus()
        editTextDistance.editableText.clear()
        editTextDistance.clearFocus()
    }

    override fun makeToast(@StringRes textForToastResId: Int) {
        val toast = Toast.makeText(applicationContext, textForToastResId, Toast.LENGTH_LONG)
        toast.setGravity(Gravity.CENTER, 0, 170)
        toast.show()
    }

    private fun setFirstLaunchMessage(switcher: Boolean) {
        if (switcher) {
            textViewFirstLaunch.visibility = View.VISIBLE
        } else {
            textViewFirstLaunch.visibility = View.INVISIBLE
        }
    }
}
