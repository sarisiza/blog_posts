package com.upakon.randomquote.di

import com.google.gson.Gson
import com.upakon.randomquote.rest.IMAGE_URL
import com.upakon.randomquote.rest.QUOTE_URL
import com.upakon.randomquote.rest.images.interceptors.ImageInterceptor
import com.upakon.randomquote.rest.images.repository.RandomImage
import com.upakon.randomquote.rest.images.repository.RandomImageImpl
import com.upakon.randomquote.rest.images.service.ImageService
import com.upakon.randomquote.rest.quotes.repository.RandomQuote
import com.upakon.randomquote.rest.quotes.repository.RandomQuoteImpl
import com.upakon.randomquote.rest.quotes.service.QuoteService
import com.upakon.randomquote.viewmodel.QuotesViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {

    single<HttpLoggingInterceptor>{
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    single<Gson>{ Gson() }

    single<CoroutineDispatcher>{Dispatchers.IO}

    single<OkHttpClient>(named("quoteOkHttp")) {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .connectTimeout(30,TimeUnit.SECONDS)
            .readTimeout(30,TimeUnit.SECONDS)
            .writeTimeout(30,TimeUnit.SECONDS)
            .build()
    }

    single<ImageInterceptor> { ImageInterceptor() }

    single<OkHttpClient>(named("imageOkHttp")) {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .addInterceptor(get<ImageInterceptor>())
            .connectTimeout(30,TimeUnit.SECONDS)
            .readTimeout(30,TimeUnit.SECONDS)
            .writeTimeout(30,TimeUnit.SECONDS)
            .build()
    }

    single<QuoteService> {
        Retrofit.Builder()
            .baseUrl(QUOTE_URL)
            .addConverterFactory(GsonConverterFactory.create(get()))
            .client(get<OkHttpClient>(named("quoteOkHttp")))
            .build()
            .create(QuoteService::class.java)
    }

    single<ImageService> {
        Retrofit.Builder()
            .baseUrl(IMAGE_URL)
            .client(get<OkHttpClient>(named("imageOkHttp")))
            .build()
            .create(ImageService::class.java)
    }

    single { RandomImageImpl(get()) } bind RandomImage::class

    single { RandomQuoteImpl(get()) } bind RandomQuote::class

}

val viewModelModule = module {

    viewModel<QuotesViewModel> {
        QuotesViewModel(
            get(),
            get(),
            get()
        )
    }

}