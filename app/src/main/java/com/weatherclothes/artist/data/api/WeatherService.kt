package com.weatherclothes.artist.data.api

import com.weatherclothes.artist.data.models_dto.CurrentWeatherDto
import com.weatherclothes.artist.data.models_dto.SearchLocationDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

private const val API_KEY = "10d2cc7b3d8c47b889c54853242412"

interface WeatherService {

    @Headers("key: $API_KEY")
    @GET("/v1/forecast.json")
    suspend fun getWeather(
        @Query("q") q: String,
        @Query("days") days: Int = 1,
        @Query("lang") lang: String = "en"
    ): Response<CurrentWeatherDto>

    @Headers("key: $API_KEY")
    @GET("/v1/search.json")
    suspend fun getPlace(
        @Query("q") q: String,
    ): Response<MutableList<SearchLocationDto>>
}