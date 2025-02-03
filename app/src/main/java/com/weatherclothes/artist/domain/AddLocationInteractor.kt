package com.weatherclothes.artist.domain

import com.weatherclothes.artist.data.repositories.DbRepository
import com.weatherclothes.artist.domain.models.SearchLocation
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddLocationInteractor @Inject constructor(
    private val repository: DbRepository
) {

    suspend fun addLocation(location: SearchLocation): Long = repository.addLocation(location)
}