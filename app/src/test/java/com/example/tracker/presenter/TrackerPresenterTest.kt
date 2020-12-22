package com.example.tracker.presenter

import com.example.tracker.data.SavedWalk
import com.example.tracker.data.model.Model
import com.example.tracker.data.model.ModelWalksScreenState
import com.example.tracker.view.TrackerView
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class TrackerPresenterTest {
    private val trackerViewMock: TrackerView = mock()
    private val modelMock :Model = mock()
    private val testObjectWalk: SavedWalk = SavedWalk("Kolomentskoe", 5.0)
    private val testListWalk: MutableList<SavedWalk> = mutableListOf(testObjectWalk)

    private var modelWalksScreenStateMock: ModelWalksScreenState =
        ModelWalksScreenState("Alice's wood", "7.0", testListWalk , false)

    @Test
    fun init() {
        var tackerViewMock = trackerViewMock.renderView(modelWalksScreenStateMock)
        verify(trackerViewMock).renderView(modelWalksScreenStateMock)
    }

//TODO  How to to test if else?
    @Test
    fun clickAddButton() {
        whenever(modelWalksScreenStateMock.isValidFields).thenReturn(true)
        whenever(modelWalksScreenStateMock.isValidFields).thenReturn(false)
    }


    @Test
    fun `update location in ModelWalks`() {
        val expectInputLocation : String = "Alice's wood"
        assertTrue(modelWalksScreenStateMock.enterLocation.equals(expectInputLocation))
   }

    @Test
    fun `update distance in ModelWalks`() {
        val expectInputDistance : String = "7.0"
        assertTrue(modelWalksScreenStateMock.enterDistance.equals(expectInputDistance))
    }

}