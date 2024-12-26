package com.weatherclothes.artist.data.mappers

import com.weatherclothes.artist.data.models_dto.LocationDto
import com.weatherclothes.artist.domain.models.Location
import javax.inject.Inject

class LocationMapper @Inject constructor() {

    fun mapLocationToDomain(locationDto: LocationDto): Location = with(locationDto) {
        Location(
            name = name
        )
    }
}