package com.weatherclothes.artist.data.mappers

import com.weatherclothes.artist.data.models_dto.CurrentWeatherDto
import com.weatherclothes.artist.data.models_dto.PlacesCurrentWeatherDto
import com.weatherclothes.artist.domain.models.CurrentWeather
import com.weatherclothes.artist.domain.models.PlacesCurrentWeather
import javax.inject.Inject

class PlacesCurrentWeatherMapper @Inject constructor(
    private val placesLocationMapper: PlacesLocationMapper,
    private val placesCurrentMapper: PlacesCurrentMapper,
) {

    fun mapToDomain(placesCurrentWeatherDto: PlacesCurrentWeatherDto?): PlacesCurrentWeather? = placesCurrentWeatherDto?.let {
        PlacesCurrentWeather(
            location = placesLocationMapper.mapPlacesLocationToDomain(it.location),
            current = placesCurrentMapper.mapPlacesCurrentToDomain(it.current),
        )
    }
}