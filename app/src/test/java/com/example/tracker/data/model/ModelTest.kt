package com.example.tracker.data.model

import android.content.Context
import android.content.SharedPreferences
import com.example.tracker.data.SavedWalk
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito


class ModelTest {
    private val listWalks: MutableList<SavedWalk> = mutableListOf()

    val listwalk : SavedWalk = SavedWalk("Kolomentskoe", 5)
    val testListWalk : MutableList<SavedWalk> = mutableListOf(listwalk)

    @Test
    fun `add Walk to list invoke add function, then equals test and original list `() {
        listWalks.add(listwalk)
        assertEquals(testListWalk, listWalks)
        println(testListWalk[0].location)
        println(testListWalk[0].distance)
    }

    @Test
    fun ` `() {
       val savedListWalks: String = "{\"Kolomentskoe\", 5}"
    }

    @Test
    fun getListWalks() {
    }

    @Test
    fun checkKeySavedWalksList() {
    }
}