package com.upakon.datastoreexample.datastore.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class PreferencesApp : Application() {

    override fun onCreate() {
        super.onCreate()

        //starting koin
        startKoin {
            androidContext(this@PreferencesApp)
            //add all the modules needed
            modules(
                dataModule,
                viewModelModule
            )
        }
    }

}