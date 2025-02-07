package com.weatherclothes.artist.domain.models

data class PlacesCurrentWeather(
    val location: PlacesLocation,
    val current: PlacesCurrent,
)