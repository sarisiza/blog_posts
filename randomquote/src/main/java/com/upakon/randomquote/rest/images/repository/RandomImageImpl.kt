package com.upakon.randomquote.rest.images.repository

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.upakon.randomquote.rest.images.service.ImageService
import com.upakon.randomquote.utils.NullResponseBody
import com.upakon.randomquote.utils.RequestError
import com.upakon.randomquote.utils.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RandomImageImpl(
    private val imageService: ImageService
) : RandomImage {
    override fun getRandomImage(category: String): Flow<UiState<Bitmap>> = flow {
        emit(UiState.LOADING)
        try {
            val response = imageService.getRandomImage(category)
            if(response.isSuccessful){
                response.body()?.let {img ->
                    val bmp = BitmapFactory.decodeStream(img.byteStream())
                    emit(UiState.SUCCESS(bmp))
                } ?: throw NullResponseBody
            } else throw RequestError
        } catch (e: Exception){
            emit(UiState.ERROR(e))
        }
    }

}