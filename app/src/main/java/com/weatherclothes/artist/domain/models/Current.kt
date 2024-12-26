package com.weatherclothes.artist.domain.models

data class Current(
    val tempC: Int,
    val tempF: Int,
    val condition: Condition,
    val windDir: String,
    val windMs: Int,
    val gustMs: Int,
    val pressureMmHg: Int,
    val humidity: Int,
    val feelsLikeC: Int,
    val feelsLikeF: Int,
)