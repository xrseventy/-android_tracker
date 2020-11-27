package com.example.tracker.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tracker.R
import com.example.tracker.data.SavedWalk

class SavedWalksAdapter(private val savedListWalks: List<SavedWalk>) :
    RecyclerView.Adapter<SavedWalkViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedWalkViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.address_and_distance_item,
            parent,
            false
        )
        return SavedWalkViewHolder(v)
    }

    override fun onBindViewHolder(holder: SavedWalkViewHolder, position: Int) {
        val currentPosition = savedListWalks[position]
        val savedListLocation = currentPosition.location
        val savedListDist = currentPosition.distance

        holder.bind(savedListLocation, savedListDist)
    }

    override fun getItemCount(): Int =
        savedListWalks.size

}