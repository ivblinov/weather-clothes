package com.weatherclothes.artist.data.repositories

import com.weatherclothes.artist.data.api.WeatherService
import com.weatherclothes.artist.data.mappers.CurrentWeatherMapper
import com.weatherclothes.artist.data.mappers.PlacesCurrentWeatherMapper
import com.weatherclothes.artist.data.mappers.SearchLocationMapper
import com.weatherclothes.artist.domain.models.CurrentWeather
import com.weatherclothes.artist.domain.models.PlacesCurrentWeather
import com.weatherclothes.artist.domain.models.SearchLocation
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    private val weatherService: WeatherService,
    private val mapper: CurrentWeatherMapper,
    private val placesMapper: PlacesCurrentWeatherMapper,
    private val searchLocationMapper: SearchLocationMapper,
) {

    suspend fun loadWeatherOfCurrentLocation(
        latitude: Double,
        longitude: Double
    ): CurrentWeather? {
        val q = "$latitude,$longitude"
        val response = weatherService.getWeather(q = q, days = 2)
        return mapper.mapToDomain(response.body())
    }

    suspend fun loadPlacesWeather(
        latitude: Double,
        longitude: Double
    ): PlacesCurrentWeather? {
        val q = "$latitude,$longitude"
        val response = weatherService.getPlacesWeather(q = q)
        return placesMapper.mapToDomain(response.body())
    }

    suspend fun loadSearchLocation(nameLocation: String): MutableList<SearchLocation> {
        val response = weatherService.getPlace(q = nameLocation)
        return searchLocationMapper.mapToDomain(response.body())
    }
}