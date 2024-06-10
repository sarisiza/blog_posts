package com.upakon.datastoreexample.datastore.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upakon.datastoreexample.datastore.model.UserSettings
import com.upakon.datastoreexample.datastore.repository.PreferencesStore
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DataStoreViewModel(
    private val preferencesStore: PreferencesStore,
    private val dispatcher : CoroutineDispatcher
) : ViewModel() {

    private val _userState : MutableStateFlow<UiState<UserSettings>> = MutableStateFlow(UiState.LOADING)
    val userSettings = _userState.asStateFlow()

    fun getUserSettings() {
        viewModelScope.launch(dispatcher) {
            _userState.value = UiState.LOADING
            try {
                preferencesStore.getSettings().collect{
                    _userState.value = UiState.SUCCESS(it)
                }
            } catch (e: Exception){
                _userState.value = UiState.ERROR(e)
            }
        }
    }

    fun updateUserSettings(username : String, age: Int){
        viewModelScope.launch(dispatcher) {
            val settings = UserSettings(username,age,false)
            preferencesStore.saveSettings(settings)
            _userState.value = UiState.SUCCESS(settings)
        }
    }

}