package com.weatherclothes.artist.data

import com.weatherclothes.artist.data.api.WeatherService
import com.weatherclothes.artist.data.models_dto.CurrentWeatherDto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    private val weatherService: WeatherService
) {
    suspend fun loadWeatherOfCurrentLocation(
        latitude: Double,
        longitude: Double): CurrentWeatherDto {
        val q = "$latitude,$longitude"
        return weatherService.getWeather(q = q)
    }
}