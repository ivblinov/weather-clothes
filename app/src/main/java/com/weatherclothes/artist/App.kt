package com.weatherclothes.artist

import android.app.Application
import com.weatherclothes.artist.di.AppComponent
import com.weatherclothes.artist.di.DaggerAppComponent

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()
    }
}