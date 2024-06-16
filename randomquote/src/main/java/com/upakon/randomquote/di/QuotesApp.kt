package com.upakon.randomquote.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class QuotesApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidContext(this@QuotesApp)
            modules(
                networkModule,
                viewModelModule
            )
        }

    }

}