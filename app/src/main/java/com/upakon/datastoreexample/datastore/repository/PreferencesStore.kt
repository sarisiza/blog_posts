package com.upakon.datastoreexample.datastore.repository

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.upakon.datastoreexample.datastore.model.UserSettings
import kotlinx.coroutines.flow.Flow

interface PreferencesStore {

    fun getSettings() : Flow<UserSettings>

    suspend fun saveSettings(settings : UserSettings)

    companion object{
        //creating the Preferences.Key
        val USERNAME = stringPreferencesKey("username")
        val AGE = intPreferencesKey("age")
        val FIRST_TIME_USER = booleanPreferencesKey("fisrtTimeUser")
    }

}