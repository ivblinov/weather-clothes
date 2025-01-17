package com.weatherclothes.artist.domain.models

data class Hour(
    val time: String,
    val tempC: Int,
    val tempF: Int,
    val condition: Condition,
)