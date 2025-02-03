package com.weatherclothes.artist.domain.models

data class LocationEntity(
    val id: Long,
    val name: String,
    val region: String,
    val country: String,
    val lat: Float,
    val lon: Float,
)