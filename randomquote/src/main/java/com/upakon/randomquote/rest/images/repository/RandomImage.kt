package com.upakon.randomquote.rest.images.repository

import android.graphics.Bitmap
import com.upakon.randomquote.utils.UiState
import kotlinx.coroutines.flow.Flow

interface RandomImage {

    fun getRandomImage(category: String) : Flow<UiState<Bitmap>>

}