package com.weatherclothes.artist.domain

import android.util.Log
import com.weatherclothes.artist.data.Repository
import com.weatherclothes.artist.data.models_dto.CurrentWeatherDto
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "MyLog"
@Singleton
class CurrentWeatherInteractor @Inject constructor(
    private val repository: Repository
) {
    suspend fun loadWeatherOfCurrentLocation(
        latitude: Double,
        longitude: Double
    ): CurrentWeatherDto {
        Log.d(TAG, "loadWeatherOfCurrentLocation: ")
        return repository.loadWeatherOfCurrentLocation(
            latitude = latitude,
            longitude = longitude
        )
    }
}