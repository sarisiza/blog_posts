package com.upakon.datastoreexample.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.KeyboardType
import com.upakon.datastoreexample.datastore.viewmodel.DataStoreViewModel

@Composable
fun RegisterScreen(
    viewModel : DataStoreViewModel,
    onClicked : () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        var username by remember {
            mutableStateOf("")
        }
        var age by remember {
            mutableStateOf("")
        }
        Text(text = "Username")
        OutlinedTextField(
            value = username, 
            onValueChange = {username = it},
            label = { Text(text = "Username")}
        )
        Text(text = "Age")
        OutlinedTextField(
            value = age,
            onValueChange = {age = it},
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )
        Button(
            onClick = {
                viewModel.updateUserSettings(username,age.toInt())
                onClicked()
            }
        ) {
            Text(text = "Save")
        }
    }
}