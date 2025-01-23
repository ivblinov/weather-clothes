package com.weatherclothes.artist.domain.models

data class ForecastDay(
    val hour: List<Hour>,
    val astro: Astro,
)