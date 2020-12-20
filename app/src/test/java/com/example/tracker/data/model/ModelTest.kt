package com.example.tracker.data.model

import android.content.Context
import android.content.SharedPreferences.Editor
import com.example.tracker.data.PreferencesProvider
import com.example.tracker.data.SavedWalk
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito


class ModelTest {
    private val listWalks: MutableList<SavedWalk> = mutableListOf()
    private val testObjectWalk : SavedWalk = SavedWalk("Kolomentskoe", 5.0)
    private val testListWalk : MutableList<SavedWalk> = mutableListOf(testObjectWalk)
    private val preferencesProviderMock :PreferencesProvider = mock()

//    @Test //too much
//    fun `add Walk to list invoke add function, then equals test and original list `() {
//        listWalks.add(testObjectWalk)
//        assertEquals(testListWalk, listWalks)
//    }
    //fun getListWalk() too much


    @Test
    fun saveSharedPref() {
       verify(preferencesProviderMock).putString(
           PreferencesProvider.KEY_STR_SAVED_WALK,
           testObjectWalk.toString()
       )

    }

    @Test
    fun getListWalks() {
        // test get walks list first launch
        //test for second launch
        //max int/ null
    }

    @Test
    fun checkKeySavedWalksList() {

       // whenever(true o)
        //whenerver должность вернуть true or false
    }
}