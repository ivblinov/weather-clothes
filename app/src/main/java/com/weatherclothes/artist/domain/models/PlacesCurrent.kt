package com.weatherclothes.artist.domain.models

data class PlacesCurrent(
    val tempC: Int,
    val tempF: Int,
    val condition: Condition,
)