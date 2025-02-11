package com.weatherclothes.artist.domain

import com.weatherclothes.artist.data.repositories.DbRepository
import com.weatherclothes.artist.domain.models.LocationEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddLocationEntityInteractor @Inject constructor(
    private val repository: DbRepository
) {

    suspend fun addLocationEntity(location: LocationEntity): Long =
        repository.addLocationEntity(location)
}