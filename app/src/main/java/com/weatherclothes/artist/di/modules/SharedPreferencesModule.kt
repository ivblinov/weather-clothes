package com.weatherclothes.artist.di.modules

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SharedPreferencesModule {

    @Singleton
    @Provides
    fun providePrefsPermission(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun provideEditor(sharedPreferences: SharedPreferences): SharedPreferences.Editor {
        return sharedPreferences.edit()
    }

    companion object {
        private const val PREFERENCES_NAME = "PREFERENCES_NAME"
    }
}