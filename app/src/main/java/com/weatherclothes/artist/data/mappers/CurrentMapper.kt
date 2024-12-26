package com.weatherclothes.artist.data.mappers

import com.weatherclothes.artist.data.models_dto.CurrentDto
import com.weatherclothes.artist.domain.models.Current
import javax.inject.Inject
import kotlin.math.roundToInt

class CurrentMapper @Inject constructor(
    private val conditionMapper: ConditionMapper
) {

    private val coefMphToMs = 0.44704f
    private val coefMbToMmHg = 0.750062f

    fun mapCurrentToDomain(currentDto: CurrentDto): Current = with(currentDto) {
        Current(
            tempC = tempC.roundToInt(),
            tempF = tempF.roundToInt(),
            condition = conditionMapper.mapConditionToDomain(currentDto.conditionDto),
            windMs = (windMph * coefMphToMs).roundToInt(),
            gustMs = (gustMph * coefMphToMs).roundToInt(),
            windDir = windDir,
            pressureMmHg = (pressureMb * coefMbToMmHg).roundToInt(),
            humidity = humidity.roundToInt(),
            feelsLikeC = feelsLikeC.roundToInt(),
            feelsLikeF = feelsLikeF.roundToInt(),
        )
    }
}