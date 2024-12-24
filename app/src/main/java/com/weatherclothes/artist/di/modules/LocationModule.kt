package com.weatherclothes.artist.di.modules

import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocationModule {

    @Singleton
    @Provides
    fun provideFusedClient(context: Context): FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    @Singleton
    @Provides
    fun provideCancellationSource(): CancellationTokenSource =
        CancellationTokenSource()
}