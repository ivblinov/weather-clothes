package com.weatherclothes.artist.domain

import com.weatherclothes.artist.data.repositories.DbRepository
import com.weatherclothes.artist.domain.models.LocationEntity
import javax.inject.Inject

class GetLocationsInteractor @Inject constructor(
    private val repository: DbRepository,
) {

    suspend fun getLocations(): List<LocationEntity> = repository.getLocations()
}