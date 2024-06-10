package com.upakon.datastoreexample.datastore.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.upakon.datastoreexample.datastore.model.UserSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PreferencesStoreImpl(
    private val dataStore: DataStore<Preferences>
) : PreferencesStore {
    override fun getSettings(): Flow<UserSettings> =
        dataStore.data.map{preference ->
            val username = preference[PreferencesStore.USERNAME] ?: ""
            val age = preference[PreferencesStore.AGE] ?: 0
            val firstTime = preference[PreferencesStore.FIRST_TIME_USER] ?: true
            UserSettings(username,age,firstTime)
        }

    override suspend fun saveSettings(settings: UserSettings) {
        dataStore.edit{preference ->
            preference[PreferencesStore.USERNAME] = settings.username
            preference[PreferencesStore.AGE] = settings.age
            preference[PreferencesStore.FIRST_TIME_USER] = settings.firstTimeUser
        }
    }
}