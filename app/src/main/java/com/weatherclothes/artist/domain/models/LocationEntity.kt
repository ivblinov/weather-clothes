package com.weatherclothes.artist.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LocationEntity(
    val id: Long,
    val name: String,
    val region: String,
    val country: String,
    val lat: Float,
    val lon: Float,
) : Parcelable