package com.weatherclothes.artist.data.models_dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PlacesCurrentWeatherDto(
    @Json(name = "location") val location: PlacesLocationDto,
    @Json(name = "current") val current: PlacesCurrentDto,
)