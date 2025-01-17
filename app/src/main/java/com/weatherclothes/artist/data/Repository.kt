package com.weatherclothes.artist.data

import com.weatherclothes.artist.data.api.WeatherService
import com.weatherclothes.artist.data.mappers.CurrentWeatherMapper
import com.weatherclothes.artist.domain.models.CurrentWeather
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    private val weatherService: WeatherService,
    private val mapper: CurrentWeatherMapper,
) {
    suspend fun loadWeatherOfCurrentLocation(
        latitude: Double,
        longitude: Double
    ): CurrentWeather? {
        val q = "$latitude,$longitude"
        val response = weatherService.getWeather(q = q, days = 2)
        return mapper.mapToDomain(response.body())
    }
}