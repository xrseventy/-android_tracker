package com.example.tracker.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.address_and_distance_item.view.*

class SavedWalkViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(location: String, distance: Double) {
        itemView.inItemLocation.text = location
        itemView.inItemDistance.text = distance.toString()
    }
}