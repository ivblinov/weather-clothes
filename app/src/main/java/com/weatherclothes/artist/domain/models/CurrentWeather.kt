package com.weatherclothes.artist.domain.models

data class CurrentWeather(
    val location: Location,
    val current: Current,
    val forecast: Forecast,
)