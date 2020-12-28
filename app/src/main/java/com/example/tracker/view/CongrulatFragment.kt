package com.example.tracker.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.tracker.R
import com.example.tracker.presenter.TrackerPresenter
import kotlinx.android.synthetic.main.activity_fields_and_progress.*
import kotlinx.android.synthetic.main.fragment_congrulat.*


/**
 * A simple [Fragment] subclass.
 */
class CongrulatFragment : Fragment() {
    // TODO: Rename and change types of parameters

    var trackerActivity: TrackerActivity = TrackerActivity()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_congrulat, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonWow.setOnClickListener {
            Toast.makeText(activity, "Hey", Toast.LENGTH_SHORT).show()
            trackerActivity.showCongrutilationsFragment(false)
        }
    }

}


