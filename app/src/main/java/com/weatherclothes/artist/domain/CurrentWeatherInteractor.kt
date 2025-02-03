package com.weatherclothes.artist.domain

import com.weatherclothes.artist.data.repositories.Repository
import com.weatherclothes.artist.domain.models.CurrentWeather
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrentWeatherInteractor @Inject constructor(
    private val repository: Repository
) {
    suspend fun loadWeatherOfCurrentLocation(
        latitude: Double,
        longitude: Double
    ): CurrentWeather? {
        return repository.loadWeatherOfCurrentLocation(
            latitude = latitude,
            longitude = longitude
        )
    }
}