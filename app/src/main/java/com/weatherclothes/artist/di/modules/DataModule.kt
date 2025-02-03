package com.weatherclothes.artist.di.modules

import android.content.Context
import com.weatherclothes.artist.data.AppDataBase
import com.weatherclothes.artist.data.dao.LocationDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Singleton
    @Provides
    fun provideAppDatabase(context: Context): AppDataBase =
        AppDataBase.get(context)

    @Singleton
    @Provides
    fun provideLocationDao(appDatabase: AppDataBase): LocationDao =
        appDatabase.locationDao()
}