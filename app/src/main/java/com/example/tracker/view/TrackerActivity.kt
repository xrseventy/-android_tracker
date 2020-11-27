package com.example.tracker.view

import android.animation.ObjectAnimator
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.example.tracker.data.App
import com.example.tracker.R
import com.example.tracker.data.SavedWalk
import com.example.tracker.ui.SavedWalksAdapter
import com.example.tracker.presenter.TrackerPresenter
import com.example.tracker.ui.UtilityTextWatcher
import kotlinx.android.synthetic.main.activity_fields_and_progress.*


import kotlinx.android.synthetic.main.activity_main.*
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

    private fun setDistanceActionListener(){
        editTextDistance.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                trackerpresenter.clickAddButton()
                true
            } else {
                false
            }
        }
    }

    override fun updateProgress(@StringRes idResString: Int, countProgress: Int) {
        textYourProgress.text = (getString(idResString, countProgress))
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

    override fun updateAdapter(listWalks: List<SavedWalk>) {
        recyclerViewSavedAddress.adapter =
            SavedWalksAdapter(listWalks)
    }

    override fun setEditText(location: String?, distance: Int?) {
        editTextLocation.setText(location)
        editTextDistance.setText(distance.toString())
    }

    override fun updateProgressBar(values: Int) {
        ObjectAnimator.ofInt(progressBarDistance, "progress", values)
            .setDuration(200)
            .start()
    }

    override fun setErrorDistance(@StringRes errorTextResId: Int) {
        editTextDistance.error = (getString(errorTextResId))
    }

    override fun setErrorLocation(@StringRes errorTextResId: Int) {
        editTextLocation.error = (getString(errorTextResId))
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
    override fun setFirstLaunchMessage(switcher : Boolean){
        if (switcher) {
            textViewFirstLaunch.visibility = View.VISIBLE
        }
        else{
            textViewFirstLaunch.visibility = View.INVISIBLE
        }
    }
}
