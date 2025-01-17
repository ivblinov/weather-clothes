package com.weatherclothes.artist.data.models_dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CurrentWeatherDto(
    @Json(name = "location") val location: LocationDto,
    @Json(name = "current") val current: CurrentDto,
    @Json(name = "forecast") val forecast: ForecastDto,
)