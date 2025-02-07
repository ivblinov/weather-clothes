package com.weatherclothes.artist.data.mappers

import com.weatherclothes.artist.data.models_dto.PlacesLocationDto
import com.weatherclothes.artist.domain.models.PlacesLocation
import javax.inject.Inject

class PlacesLocationMapper @Inject constructor() {

    fun mapPlacesLocationToDomain(placesLocationDto: PlacesLocationDto): PlacesLocation = with(placesLocationDto) {
        PlacesLocation(
            name = name,
        )
    }
}