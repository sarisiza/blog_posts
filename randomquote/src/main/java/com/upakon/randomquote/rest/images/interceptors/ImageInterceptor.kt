package com.upakon.randomquote.rest.images.interceptors

import com.upakon.randomquote.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class ImageInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        chain.request().newBuilder().apply {
            addHeader(
                "x-api-key",
                BuildConfig.IMG_API_KEY
            )
            addHeader(
                "Accept",
                "image/jpg"
            )
        }.build().also { return chain.proceed(it) }
    }

}