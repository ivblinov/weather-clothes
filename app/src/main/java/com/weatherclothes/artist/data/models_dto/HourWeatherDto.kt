package com.weatherclothes.artist.data.models_dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HourWeatherDto(
    @Json(name = "forecast") val forecast: ForecastDto,
)