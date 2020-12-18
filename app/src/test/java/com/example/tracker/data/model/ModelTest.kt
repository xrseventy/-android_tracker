package com.example.tracker.data.model

import com.example.tracker.data.SavedWalk
import org.junit.Assert.*
import org.junit.Test


class ModelTest {
    private val listWalks: MutableList<SavedWalk> = mutableListOf()
    private val testObjectWalk : SavedWalk = SavedWalk("Kolomentskoe", 5.0)
    private val testListWalk : MutableList<SavedWalk> = mutableListOf(testObjectWalk)

    @Test
    fun `add Walk to list invoke add function, then equals test and original list `() {
        listWalks.add(testObjectWalk)
        assertEquals(testListWalk, listWalks)
    }

    @Test
    fun saveSharedPref() {
    }

    @Test
    fun getListWalks() {
    }

    @Test
    fun checkKeySavedWalksList() {
    }
}