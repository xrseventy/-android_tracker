package com.example.tracker.common

import android.app.Application

class App : Application() {

    val model: Model = Model()

    class Model() {
        private val listWalks: MutableList<SavedWalk> = mutableListOf()

        fun addWalk(walk: SavedWalk) {
            listWalks.add(walk)
        }

        fun getListWalk(): List<SavedWalk> {
            return listWalks
        }
    }

}
