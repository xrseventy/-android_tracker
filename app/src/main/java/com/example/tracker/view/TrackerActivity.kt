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
import androidx.fragment.app.FragmentManager
import com.example.tracker.R
import com.example.tracker.data.App
import com.example.tracker.data.SavedWalk
import com.example.tracker.data.model.ModelWalksScreenState
import com.example.tracker.presenter.TrackerPresenter
import com.example.tracker.ui.SavedWalksAdapter
import com.example.tracker.ui.UtilityTextWatcher
import kotlinx.android.synthetic.main.activity_fields_and_progress.*
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.distance_field.*
import kotlinx.android.synthetic.main.fragment_congrulat.*
import kotlinx.android.synthetic.main.location_field.*


class TrackerActivity : AppCompatActivity(), TrackerView {

    private lateinit var trackerPresenter: TrackerPresenter
    private val congrulatFragment = CongrulatFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val app = (application as App)
        trackerPresenter = TrackerPresenter(this, app.model)
        addTextWatcherLocation()
        addTextWatcherDistance()
        trackerPresenter.init()
        setDistanceActionListener()
        buttonAdd.setOnClickListener {
            trackerPresenter.clickAddButton()
        }
        if
    }

    override fun renderView(model: ModelWalksScreenState) {
        setFirstLaunchMessage(model.isFirstLaunchMessageVisible)
        updateProgressText(model.totalDistance)
        updateProgressBar(model.totalDistance)
        updateAdapter(model.listWalks)
        initCongratFragment()
        if (!model.isEnterDistanceValid && model.areErrorsVisible) {
            setErrorDistance(model.distanceErrorResId)
        }
        if (!model.isEnterLocationValid && model.areErrorsVisible) {
            setErrorLocation(model.locationErrorResId)
        }

    }
    override fun renderFragment(model: ModelWalksScreenState) {
        if(model.congrulationVisible) {
            setVisibilityContainerFragment(model.congrulationVisible)
            setCongrutilationsText(model.enterDistance.toDouble())
            showCongratFragment()
        }
        else
        {
            hideCongrutilationsFragment()
            setVisibilityContainerFragment(model.congrulationVisible)
        }
    }

    private fun initCongratFragment() {
        supportFragmentManager.beginTransaction()
            .apply {replace(R.id.frameLayoutFragment, congrulatFragment) }
            .addToBackStack(null)
            .commit()
        Log.d(this.toString(), "init fragment")
    }
    private fun showCongratFragment() {
        Log.d(this.toString(), "show fragment")
        supportFragmentManager.beginTransaction()
            .show(congrulatFragment)
            .commit()
    }
    override fun hideCongrutilationsFragment(){
        Log.d(this.toString(), "hide fragment")
        supportFragmentManager.beginTransaction()
            .hide(congrulatFragment)
            .commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        Log.d(this.toString(), "clear fragment")
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }


     fun setVisibilityContainerFragment(switcher: Boolean) {
        if (switcher) {
            frameLayoutFragment?.visibility = View.VISIBLE
            Log.d(this.toString(), "container fragment visible")
        } else {
            frameLayoutFragment?.visibility = View.INVISIBLE
            Log.d(this.toString(), "container fragment invisible")
        }
    }

    private fun setCongrutilationsText(countProgress: Double) {

        textViewCongratInfo.text = (getString(R.string.text_view_congrat_info, countProgress))
        Log.d(this.toString(), "set text fragment fragment")
        Log.d(this.toString(), "$countProgress  fragment")
    }

    override fun closeKeyboards() {
        closeKeyboard(editTextLocation)
        closeKeyboard(editTextDistance)
    }


    private fun setDistanceActionListener() {
        editTextDistance.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                trackerPresenter.clickAddButton()
                true
            } else {
                false
            }
        }
    }

    private fun closeKeyboard(view: View) {
        val inputMethodEditText =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodEditText.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun addTextWatcherLocation() {
        editTextLocation.addTextChangedListener(UtilityTextWatcher(trackerPresenter::onLocationTextChanged))
    }

    private fun addTextWatcherDistance() {
        editTextDistance.addTextChangedListener(UtilityTextWatcher(trackerPresenter::onDistanceTextChanged))
    }

    private fun updateAdapter(listWalks: List<SavedWalk>) {
        recyclerViewSavedAddress.adapter = SavedWalksAdapter(listWalks)
    }

    private fun updateProgressText(countProgress: Double) {
        textYourProgress.text = (getString(R.string.your_progress, countProgress))
    }

    private fun updateProgressBar(values: Double) {
        ObjectAnimator.ofInt(progressBarDistance, "progress", values.toInt())
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

    private fun setFirstLaunchMessage(switcher: Boolean) {
        if (switcher) {
            textViewFirstLaunch.visibility = View.VISIBLE
        } else {
            textViewFirstLaunch.visibility = View.INVISIBLE
        }
    }

    //TODO del toast
    override fun toast() {
        val toastAdd = Toast.makeText(applicationContext, "Best!", Toast.LENGTH_LONG)
        toastAdd.setGravity(Gravity.TOP, 0, 170)
        toastAdd.show()
    }


}
