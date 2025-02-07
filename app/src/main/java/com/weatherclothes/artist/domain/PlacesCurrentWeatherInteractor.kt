package com.weatherclothes.artist.domain

import com.weatherclothes.artist.data.repositories.Repository
import com.weatherclothes.artist.domain.models.PlacesCurrentWeather
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlacesCurrentWeatherInteractor @Inject constructor(
    private val repository: Repository
) {

    suspend fun loadPlacesWeather(
        latitude: Double,
        longitude: Double
    ): PlacesCurrentWeather? {
        return repository.loadPlacesWeather(
            latitude = latitude,
            longitude = longitude
        )
    }
}