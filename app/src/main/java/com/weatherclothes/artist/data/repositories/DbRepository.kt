package com.weatherclothes.artist.data.repositories

import com.weatherclothes.artist.data.dao.LocationDao
import com.weatherclothes.artist.data.mappers_db.AddLocationMapperDb
import com.weatherclothes.artist.data.mappers_db.GetLocationsMapperDb
import com.weatherclothes.artist.domain.models.LocationEntity
import com.weatherclothes.artist.domain.models.SearchLocation
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DbRepository @Inject constructor(
    private val localSource: LocationDao,
    private val mapper: AddLocationMapperDb,
    private val getLocationsMapperDb: GetLocationsMapperDb,
) {

    suspend fun addLocation(location: SearchLocation): Long =
        if (!checkInTable(location)) {
            localSource.add(mapper.mapToLocationEntity(location))
        } else 0L

    suspend fun checkInTable(location: SearchLocation): Boolean {
        val locations = localSource.getAll().map { mapper.mapToSearchLocation(it) }
        return locations.contains(location)
    }

    suspend fun getLocations(): List<LocationEntity> =
        localSource.getAll().map { getLocationsMapperDb.mapToLocationEntity(it) }
}