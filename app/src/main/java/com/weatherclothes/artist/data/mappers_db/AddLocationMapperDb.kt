package com.weatherclothes.artist.data.mappers_db

import com.weatherclothes.artist.data.models_db.LocationEntityDb
import com.weatherclothes.artist.domain.models.LocationEntity
import com.weatherclothes.artist.domain.models.SearchLocation
import javax.inject.Inject

class AddLocationMapperDb @Inject constructor() {

    fun mapToLocationEntity(searchLocation: SearchLocation): LocationEntityDb = with(searchLocation) {
        LocationEntityDb(
            id = 0,
            name = name,
            region = region,
            country = country,
            lat = lat,
            lon = lon,
        )
    }

    fun mapToSearchLocation(locationEntityDb: LocationEntityDb): SearchLocation = with(locationEntityDb) {
        SearchLocation(
            name = name,
            region = region,
            country = country,
            lat = lat,
            lon = lon,
        )
    }

    fun mapToLocationEntityDb(location: LocationEntity): LocationEntityDb = with(location) {
        LocationEntityDb(
            id = 0,
            name = name,
            region = region,
            country = country,
            lat = lat,
            lon = lon,
        )
    }

    fun mapToLocationEntityDbWithId(location: LocationEntity): LocationEntityDb = with(location) {
        LocationEntityDb(
            id = id,
            name = name,
            region = region,
            country = country,
            lat = lat,
            lon = lon,
        )
    }
}