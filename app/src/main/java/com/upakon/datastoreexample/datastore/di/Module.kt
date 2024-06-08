package com.upakon.datastoreexample.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.upakon.datastoreexample.datastore.repository.PreferencesStore
import com.upakon.datastoreexample.datastore.repository.PreferencesStoreImpl
import com.upakon.datastoreexample.datastore.viewmodel.DataStoreViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private const val USER_SETTINGS = "user_settings"

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = USER_SETTINGS
)
val dataModule = module {
    //data store
    single<PreferencesStore> {
        PreferencesStoreImpl(androidContext().dataStore)
    }
}

val viewModelModule = module {
    viewModel {
        DataStoreViewModel(
            preferencesStore = get(),
            dispatcher = Dispatchers.IO
        )
    }
}