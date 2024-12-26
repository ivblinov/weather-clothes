package com.weatherclothes.artist.data.models_dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CurrentDto(
    @Json(name = "temp_c") val tempC: Float,
    @Json(name = "temp_f") val tempF: Float,
    @Json(name = "condition") val conditionDto: ConditionDto,
    @Json(name = "wind_mph") val windMph: Float,
    @Json(name = "gust_mph") val gustMph: Float,
    @Json(name = "wind_dir") val windDir: String,
    @Json(name = "pressure_mb") val pressureMb: Float,
    @Json(name = "humidity") val humidity: Float,
    @Json(name = "feelslike_c") val feelsLikeC: Float,
    @Json(name = "feelslike_f") val feelsLikeF: Float,
)