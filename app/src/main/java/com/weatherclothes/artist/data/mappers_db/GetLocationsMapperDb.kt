package com.weatherclothes.artist.data.mappers_db

import com.weatherclothes.artist.data.models_db.LocationEntityDb
import com.weatherclothes.artist.domain.models.LocationEntity
import javax.inject.Inject

class GetLocationsMapperDb @Inject constructor() {

    fun mapToLocationEntity(locationsDb: LocationEntityDb): LocationEntity = with(locationsDb) {
        LocationEntity(
            id = id,
            name = name,
            region = region,
            country = country,
            lat = lat,
            lon = lon,
        )
    }
}