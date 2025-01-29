package com.weatherclothes.artist.domain

import com.weatherclothes.artist.data.Repository
import com.weatherclothes.artist.domain.models.SearchLocation
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchLocationInteractor @Inject constructor(
    private val repository: Repository
) {
    suspend fun loadSearchLocation(nameLocation: String): MutableList<SearchLocation> {
        return repository.loadSearchLocation(nameLocation = nameLocation)
    }
}