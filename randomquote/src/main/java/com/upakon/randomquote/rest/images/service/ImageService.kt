package com.upakon.randomquote.rest.images.service

import com.upakon.randomquote.rest.RANDOM_IMAGE
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ImageService {

    @GET(RANDOM_IMAGE)
    suspend fun getRandomImage(
        @Query("category") category: String
    ) : Response<ResponseBody>

}