package com.upakon.randomquote.utils

sealed class UiState<out T> {

    data object LOADING : UiState<Nothing>()

    data class SUCCESS<T>(val respopnse : T) : UiState<T>()

    data class ERROR(val error : Exception) : UiState<Nothing>()

}