package com.weatherclothes.artist.data.mappers

import com.weatherclothes.artist.data.models_dto.HourDto
import com.weatherclothes.artist.domain.models.Hour
import javax.inject.Inject
import kotlin.math.roundToInt

class HourMapper @Inject constructor(
    private val conditionMapper: ConditionMapper
) {

    fun mapHourToDomain(hourDto: HourDto): Hour = with(hourDto) {
        Hour(
            time = time,
            tempC = tempC.roundToInt(),
            tempF = tempF.roundToInt(),
            condition = conditionMapper.mapConditionToDomain(hourDto.conditionDto),
        )
    }
}