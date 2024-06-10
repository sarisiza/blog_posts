package com.upakon.datastoreexample.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.upakon.datastoreexample.datastore.viewmodel.DataStoreViewModel
import com.upakon.datastoreexample.datastore.viewmodel.UiState

@Composable
fun HomeScreen(
    viewModel: DataStoreViewModel
){
    when(val settingsState = viewModel.userSettings.collectAsState().value){
        is UiState.ERROR -> {}
        UiState.LOADING -> {}
        is UiState.SUCCESS -> {
            Column(modifier = Modifier.fillMaxWidth()) {
                val settings = settingsState.information
                Text(text = "Welcome, ${settings.username}")
                Text(text = "Your age: ${settings.age}")
            }
        }
    }
}