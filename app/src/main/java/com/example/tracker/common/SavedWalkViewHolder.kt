package com.example.tracker.common

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.address_and_distance_item.view.*

class SavedWalkViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(location: String, distance: Int) {
        itemView.inItemLocation.text = location
        itemView.inItemDistance.text = distance.toString()
    }
}