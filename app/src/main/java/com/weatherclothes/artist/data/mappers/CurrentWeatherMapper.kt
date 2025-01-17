package com.weatherclothes.artist.data.mappers

import com.weatherclothes.artist.data.models_dto.CurrentWeatherDto
import com.weatherclothes.artist.domain.models.CurrentWeather
import javax.inject.Inject

class CurrentWeatherMapper @Inject constructor(
    private val locationMapper: LocationMapper,
    private val currentMapper: CurrentMapper,
    private val forecastMapper: ForecastMapper,
) {

    fun mapToDomain(currentWeatherDto: CurrentWeatherDto?): CurrentWeather? = currentWeatherDto?.let {
        CurrentWeather(
            location = locationMapper.mapLocationToDomain(it.location),
            current = currentMapper.mapCurrentToDomain(it.current),
            forecast = forecastMapper.mapForecastToDomain(it.forecast),
        )
    }
}