package com.weatherclothes.artist.data.mappers

import com.weatherclothes.artist.data.models_dto.PlacesCurrentDto
import com.weatherclothes.artist.domain.models.PlacesCurrent
import javax.inject.Inject
import kotlin.math.roundToInt

class PlacesCurrentMapper @Inject constructor (
    private val conditionMapper: ConditionMapper
) {

    fun mapPlacesCurrentToDomain(placesCurrentDto: PlacesCurrentDto): PlacesCurrent = with(placesCurrentDto) {
        PlacesCurrent(
            tempC = tempC.roundToInt(),
            tempF = tempF.roundToInt(),
            condition = conditionMapper.mapConditionToDomain(placesCurrentDto.conditionDto),
        )
    }
}