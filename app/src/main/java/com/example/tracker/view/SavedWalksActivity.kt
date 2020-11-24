package com.example.tracker.view

import android.animation.ObjectAnimator
import android.content.Context
import android.os.Bundle
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
    private lateinit var preferencesProvider: PreferencesProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val app = (application as App)
        preferencesProvider = PreferencesProvider(applicationContext)
        savedWalksPresenterImpl = SavedWalksPresenterImpl(this, app.model)
        savedWalksPresenterImpl.init()
        addTextWatcherLocation()
        addTextWatcherDistance()
        setEditText()
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
        editTextLocation.addTextChangedListener(ClearTextWatchers { s ->
            savedWalksPresenterImpl.onLocationTextChanged(
                s
            )
        })
    }

    private fun addTextWatcherDistance() {
        editTextDistance.addTextChangedListener(ClearTextWatchers { s ->
            savedWalksPresenterImpl.onDistanceTextChanged(
                s
            )
        })
    }

    override fun updateAdapter(listWalks: List<SavedWalk>) {
        recyclerViewSavedAddress.adapter =
            SavedWalksAdapter(listWalks)
    }

    private fun setEditText() {
        editTextLocation.setText(preferencesProvider.getString(PreferencesProvider.KEY_LOCATION))
        editTextDistance.setText(
            preferencesProvider.getInt(PreferencesProvider.KEY_DISTANCE).toString()
        )
    }

    override fun saveSharedPref(savedLocation: String, savedDistance: Int) {
        preferencesProvider.putInt(PreferencesProvider.KEY_DISTANCE, savedDistance)
        preferencesProvider.putString(PreferencesProvider.KEY_LOCATION, savedLocation)
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

    override fun makeToast() {
        val toastAdd = Toast.makeText(applicationContext, "Added", Toast.LENGTH_LONG)
        toastAdd.setGravity(Gravity.TOP, 0, 170)
        toastAdd.show()
    }
}
