package com.example.tracker.view

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.tracker.R
import kotlinx.android.synthetic.main.fragment_congrulat.*


/**
 * A simple [Fragment] subclass.
 */
class CongrulatFragment : Fragment() {
    // TODO: Rename and change types of parameters


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_congrulat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val trackerActivity = (activity as TrackerActivity)
        buttonWow.setOnClickListener {
            Log.d(this.toString(), "hide fragment on buttonWow")
            trackerActivity.hideCongrutilationsFragment()
        }
    }
}


