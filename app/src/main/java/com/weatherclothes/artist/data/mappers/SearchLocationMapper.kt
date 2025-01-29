package com.weatherclothes.artist.data.mappers

import com.weatherclothes.artist.data.models_dto.SearchLocationDto
import com.weatherclothes.artist.domain.models.SearchLocation
import javax.inject.Inject

class SearchLocationMapper @Inject constructor() {

    fun mapToDomain(searchLocationDtoList: MutableList<SearchLocationDto>?): MutableList<SearchLocation> {
        val searchLocationList = mutableListOf<SearchLocation>()
        searchLocationDtoList?.forEach { it
            searchLocationList.add(
                SearchLocation(
                    name = it.name,
                    region = it.region,
                    country = it.country,
                    lat = it.lat,
                    lon = it.lon,
                )
            )
        }
        return searchLocationList
    }
}