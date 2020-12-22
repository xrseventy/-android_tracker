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

    @Test
    fun `putString is invoked`() {
        var putStrMock = preferencesProviderMock.putString("KEY", "String")
        verify(preferencesProviderMock).putString(
            "KEY",
            "String"
        )
    }

    @Test
    fun `getString for first launch app emptyList`() {
        whenever(preferencesProviderMock.getString("KEY_FIRST_LAUNCH_LIST")).thenReturn("")
    }

    @Test
    fun `getString for second launch app`() {
        whenever(preferencesProviderMock.getString("KEY_SECOND_LAUNCH_LIST")).thenReturn("Kolomentskoe, 5.0")
    }

    @Test
    fun `getString for null list`() {
        whenever(preferencesProviderMock.getString("KEY_NULL_LIST")).thenReturn(null)
    }

    @Test
    fun `hasKey with key true`() {
        whenever(preferencesProviderMock.hasKey("KEY")).thenReturn(true)
    }

    @Test
    fun `hasKey without key`() {
        whenever(preferencesProviderMock.hasKey("")).thenReturn(false)
    }

}