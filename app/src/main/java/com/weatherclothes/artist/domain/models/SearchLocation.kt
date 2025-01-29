package com.weatherclothes.artist.domain.models

data class SearchLocation(
    val name: String,
    val region: String,
    val country: String,
    val lat: Float,
    val lon: Float,
)