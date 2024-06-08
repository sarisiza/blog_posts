package com.upakon.datastoreexample.datastore.viewmodel

sealed class UiState<out T> {

    object LOADING : UiState<Nothing>()

    data class SUCCESS<T>(val information : T) : UiState<T>()

    data class ERROR(val error : Exception) : UiState<Nothing>()

}