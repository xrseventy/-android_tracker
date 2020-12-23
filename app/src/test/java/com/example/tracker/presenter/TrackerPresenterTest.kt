package com.example.tracker.presenter

import com.example.tracker.data.model.Model
import com.example.tracker.view.TrackerView
import com.nhaarman.mockitokotlin2.*
import org.junit.Test

class TrackerPresenterTest {

    private val trackerViewMock: TrackerView = mock()
    private val modelMock: Model = mock()
    private val trackerPresenter: TrackerPresenter = TrackerPresenter(trackerViewMock, modelMock)

    @Test
    fun `valid cases clickAddButton`() {
        trackerPresenter.onLocationTextChanged("Sokolniki")
        trackerPresenter.onDistanceTextChanged("8.0")
        trackerPresenter.clickAddButton()
        verify(modelMock).addWalk(any())
        verify(modelMock).saveSharedPref(any())
        verify(trackerViewMock).renderView(any())
    }

    @Test
    fun `error cases clickAddButton`() {
        trackerPresenter.onLocationTextChanged("")
        trackerPresenter.onDistanceTextChanged("1")
        trackerPresenter.clickAddButton()
        verify(modelMock, never()).addWalk(any())
        //another way to do some opposite for verify)
        //verify(modelMock, Mockito.times(0)).addWalk(any())
        verify(trackerViewMock).renderView(any())
    }

}