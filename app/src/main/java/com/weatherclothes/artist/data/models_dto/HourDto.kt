package com.weatherclothes.artist.data.models_dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HourDto(
    @Json(name = "time") val time: String,
    @Json(name = "temp_c") val tempC: Float,
    @Json(name = "temp_f") val tempF: Float,
    @Json(name = "condition") val conditionDto: ConditionDto,
)