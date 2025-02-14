package com.weatherclothes.artist.domain

import com.weatherclothes.artist.data.repositories.DbRepository
import com.weatherclothes.artist.domain.models.LocationEntity
import javax.inject.Inject

class DeleteLocationInteractor @Inject constructor(
    private val repository: DbRepository,
) {

    suspend fun deleteLocation(location: LocationEntity) = repository.deleteLocation(location)
}