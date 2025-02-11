package com.weatherclothes.artist.domain

import com.weatherclothes.artist.data.repositories.DbRepository
import javax.inject.Inject

class ClearTableInteractor @Inject constructor(
    private val repository: DbRepository
) {

    suspend fun clear() = repository.clear()
}