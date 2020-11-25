package com.example.tracker.view

import android.animation.ObjectAnimator
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.example.tracker.R
import com.example.tracker.common.*
import com.example.tracker.presenter.SavedWalksPresenterImpl
import kotlinx.android.synthetic.main.activity_main.*

class SavedWalksActivity : AppCompatActivity(), SavedWalksView {

    private lateinit var savedWalksPresenterImpl: SavedWalksPresenterImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val app = (application as App)
        savedWalksPresenterImpl = SavedWalksPresenterImpl(this, app.model)
        addTextWatcherLocation()
        addTextWatcherDistance()
        savedWalksPresenterImpl.init()
        buttonAdd.setOnClickListener {
            savedWalksPresenterImpl.clickAddButton()
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
        editTextLocation.addTextChangedListener(UtilityTextWatcher { s ->
            savedWalksPresenterImpl.onLocationTextChanged(
                s
            )
        })
    }


    private fun addTextWatcherDistance() {
        editTextDistance.addTextChangedListener(UtilityTextWatcher { s ->
            savedWalksPresenterImpl.onDistanceTextChanged(
                s
            )
        })
    }

    override fun updateAdapter(listWalks: List<SavedWalk>) {
        recyclerViewSavedAddress.adapter =
            SavedWalksAdapter(listWalks)
    }

    override fun setEditText(location: String, distance: Int) {
//        editTextLocation.setText(preferencesProvider?.getString(PreferencesProvider.KEY_STR_LOCATION))
//        editTextDistance.setText(
//            preferencesProvider?.getInt(PreferencesProvider.KEY_INT_DISTANCE).toString()
//        )
        editTextLocation.setText(location)
        editTextDistance.setText(distance.toString())
    }

//    override fun saveSharedPref(savedLocation: String, savedDistance: Int) {
//        preferencesProvider.putInt(PreferencesProvider.KEY_INT_DISTANCE, savedDistance)
//        preferencesProvider.putString(PreferencesProvider.KEY_STR_LOCATION, savedLocation)
//    }

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
        val toastAdd = Toast.makeText(applicationContext, textForToastResId, Toast.LENGTH_LONG)
        toastAdd.setGravity(Gravity.TOP, 0, 170)
        toastAdd.show()
    }
}
