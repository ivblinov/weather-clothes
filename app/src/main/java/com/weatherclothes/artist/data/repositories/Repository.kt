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

private const val TAG = "MyLog"
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
        try {
            val q = "$latitude,$longitude"
            val response = weatherService.getWeather(q = q, days = 2)
            return if (response.isSuccessful)
                mapper.mapToDomain(response.body())
            else null
        } catch (_: Exception) {
            return null
        }
    }

    suspend fun loadPlacesWeather(
        latitude: Double,
        longitude: Double
    ): PlacesCurrentWeather? {
        try {
            val q = "$latitude,$longitude"
            val response = weatherService.getPlacesWeather(q = q)
            return if (response.isSuccessful)
                placesMapper.mapToDomain(response.body())
            else null
        } catch (_: Exception) {
            return null
        }
    }

    suspend fun loadSearchLocation(nameLocation: String): MutableList<SearchLocation> {
        try {
            val response = weatherService.getPlace(q = nameLocation)
            return if (response.isSuccessful)
                searchLocationMapper.mapToDomain(response.body())
            else mutableListOf()
        } catch (_: Exception) {
            return mutableListOf()
        }
    }
}