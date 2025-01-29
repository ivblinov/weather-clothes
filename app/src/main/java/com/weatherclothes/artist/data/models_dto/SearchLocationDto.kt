package com.weatherclothes.artist.data.models_dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchLocationDto(
    @Json(name = "name") val name: String,
    @Json(name = "region") val region: String,
    @Json(name = "country") val country: String,
    @Json(name = "lat") val lat: Float,
    @Json(name = "lon") val lon: Float,
)