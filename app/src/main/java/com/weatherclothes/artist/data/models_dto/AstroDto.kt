package com.weatherclothes.artist.data.models_dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AstroDto(
    @Json(name = "sunrise") val sunrise: String,
    @Json(name = "sunset") val sunset: String,
)