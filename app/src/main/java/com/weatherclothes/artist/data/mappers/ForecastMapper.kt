package com.weatherclothes.artist.data.mappers

import com.weatherclothes.artist.data.models_dto.ForecastDto
import com.weatherclothes.artist.domain.models.Forecast
import com.weatherclothes.artist.domain.models.ForecastDay
import javax.inject.Inject

class ForecastMapper @Inject constructor(
    private val forecastDayMapper: ForecastDayMapper
) {

    fun mapForecastToDomain(forecastDto: ForecastDto): Forecast {
        val forecastDayList = mutableListOf<ForecastDay>()
        forecastDto.forecastDay.forEach {
            forecastDayList.add(forecastDayMapper.mapForecastDayToDomain(it))
        }
        return Forecast(
            forecastDay = forecastDayList.toList()
        )
    }
}