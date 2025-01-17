package com.weatherclothes.artist.utils

import android.content.Context
import com.weatherclothes.artist.App
import com.weatherclothes.artist.di.AppComponent

fun Context.appComponent(): AppComponent {
    return when (this) {
        is App -> appComponent
        else -> this.applicationContext.appComponent()
    }
}