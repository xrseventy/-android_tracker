package com.example.tracker.presenter

import com.example.tracker.data.SavedWalk
import com.example.tracker.data.model.Model
import com.example.tracker.data.model.ModelWalksScreenState
import com.example.tracker.view.TrackerView
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
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
    fun `init invoke renderView `() {
        var tackerViewMock = trackerViewMock.renderView(modelWalksScreenStateMock)
        verify(trackerViewMock).renderView(modelWalksScreenStateMock)
    }

    @Test
    fun `success updateModelWalks after click `() {

        updateModelWalksTest("Alice's wood", "7.0", testListWalk , false)
        val expectModelWalksScreenStateMock: ModelWalksScreenState =
            ModelWalksScreenState("Alice's wood", "7.0", testListWalk , false)
        assertEquals( expectModelWalksScreenStateMock.enterLocation, modelWalksScreenStateMock.enterLocation)
        assertEquals( expectModelWalksScreenStateMock.enterDistance, modelWalksScreenStateMock.enterDistance)
        assertEquals( expectModelWalksScreenStateMock.listWalks, modelWalksScreenStateMock.listWalks)
        assertEquals( expectModelWalksScreenStateMock.areErrorsVisible, modelWalksScreenStateMock.areErrorsVisible)
    }

    @Test
    fun `location and distance errors updateModelWalks after click `() {
        updateModelWalksTest("", "7.0", testListWalk , true)
        val expectModelWalksScreenStateMock: ModelWalksScreenState =
            ModelWalksScreenState("", "7.0", testListWalk , true)
        assertEquals( expectModelWalksScreenStateMock.enterLocation, modelWalksScreenStateMock.enterLocation)
        assertEquals( expectModelWalksScreenStateMock.enterDistance, modelWalksScreenStateMock.enterDistance)
        assertEquals( expectModelWalksScreenStateMock.areErrorsVisible, modelWalksScreenStateMock.areErrorsVisible)

    }

    @Test
    fun `distance error updateModelWalks after click `() {
        updateModelWalksTest("", "7.0", testListWalk , true)
        val expectModelWalksScreenStateMock: ModelWalksScreenState =
            ModelWalksScreenState("", "7.0", testListWalk , true)
        assertEquals( expectModelWalksScreenStateMock.enterLocation, modelWalksScreenStateMock.enterLocation)
        assertEquals( expectModelWalksScreenStateMock.enterDistance, modelWalksScreenStateMock.enterDistance)
        assertEquals( expectModelWalksScreenStateMock.areErrorsVisible, modelWalksScreenStateMock.areErrorsVisible)
    }

    @Test
    fun `distance and location error updateModelWalks after click `() {
        updateModelWalksTest("", "", testListWalk , true)
        val expectModelWalksScreenStateMock: ModelWalksScreenState =
            ModelWalksScreenState("", "", testListWalk , true)
        assertEquals( expectModelWalksScreenStateMock.enterLocation, modelWalksScreenStateMock.enterLocation)
        assertEquals( expectModelWalksScreenStateMock.enterDistance, modelWalksScreenStateMock.enterDistance)
        assertEquals( expectModelWalksScreenStateMock.areErrorsVisible, modelWalksScreenStateMock.areErrorsVisible)
    }

    @Test
    fun `update enterLocation in ModelWalks`() {
        val expectInputLocation : String = "Alice's wood"
        assertTrue(modelWalksScreenStateMock.enterLocation == expectInputLocation)
   }

    @Test
    fun `update enterDistance in ModelWalks`() {
        val expectInputDistance : String = "7.0"
        assertTrue(modelWalksScreenStateMock.enterDistance == expectInputDistance)
    }

    private fun updateModelWalksTest(
        location: String = modelWalksScreenStateMock.enterLocation,
        distance: String = modelWalksScreenStateMock.enterDistance,
        listWalks: List<SavedWalk> = modelWalksScreenStateMock.listWalks,
        areErrorVisible: Boolean = modelWalksScreenStateMock.areErrorsVisible
    ) {
        modelWalksScreenStateMock = ModelWalksScreenState(location, distance, listWalks, areErrorVisible)
    }

}