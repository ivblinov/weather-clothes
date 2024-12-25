package com.weatherclothes.artist.data.api

import com.weatherclothes.artist.data.models_dto.CurrentWeatherDto
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("/forecast.json")
    suspend fun getWeather(
        @Query("key") key: String = "10d2cc7b3d8c47b889c54853242412",
        @Query("q") q: String,
        @Query("days") days: Int = 1
    ): CurrentWeatherDto
}