package com.weatherclothes.artist.data.mappers

import com.weatherclothes.artist.data.models_dto.ForecastDayDto
import com.weatherclothes.artist.domain.models.ForecastDay
import com.weatherclothes.artist.domain.models.Hour
import javax.inject.Inject

class ForecastDayMapper @Inject constructor(
    private val hourMapper: HourMapper
) {

    fun mapForecastDayToDomain(forecastDayDto: ForecastDayDto): ForecastDay {
        val hourList = mutableListOf<Hour>()
        forecastDayDto.hour.forEach {
            hourList.add(hourMapper.mapHourToDomain(it))
        }
        return ForecastDay(
            hour = hourList.toList()
        )
    }
}