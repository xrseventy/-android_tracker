package com.example.tracker.data.model

import android.content.Context
import android.content.SharedPreferences.Editor
import com.example.tracker.data.PreferencesProvider
import com.example.tracker.data.SavedWalk
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito


class ModelTest {

    private val preferencesProviderMock: PreferencesProvider = mock()
    private val model: Model = Model(preferencesProviderMock)

    @Test
    fun `putString is invoked`() {
        var putStrMock = preferencesProviderMock.putString(KEY_STR_SAVED_WALK, "String")
        verify(preferencesProviderMock).putString(
            KEY_STR_SAVED_WALK,
            "String"
        )
    }

    @Test
    fun `getString for first launch app emptyList`() {
        whenever(preferencesProviderMock.getString(KEY_STR_SAVED_WALK)).thenReturn("")
        val testResult: String? = model.getListWalksFromSharedPref()
        assertEquals("", testResult)
    }

    @Test
    fun `getString for second launch app`() {
        whenever(preferencesProviderMock.getString(KEY_STR_SAVED_WALK)).thenReturn("Kolomentskoe, 5.0")
        val testResult: String? = model.getListWalksFromSharedPref()
        assertEquals("Kolomentskoe, 5.0", testResult)
    }

    @Test
    fun `getString for null list`() {
        whenever(preferencesProviderMock.getString(KEY_STR_SAVED_WALK)).thenReturn(null)
        val testResult: String? = model.getListWalksFromSharedPref()
        assertEquals(null, testResult)
    }

    @Test
    fun `hasKey with key true`() {
        whenever(preferencesProviderMock.hasKey(KEY_STR_SAVED_WALK)).thenReturn(true)
        model.checkKeySavedWalksList()
    }

    @Test
    fun `hasKey without key`() {
        whenever(preferencesProviderMock.hasKey("")).thenReturn(false)
        model.checkKeySavedWalksList()
    }

    companion object {
        const val KEY_STR_SAVED_WALK = "SavedWalk"
    }
}