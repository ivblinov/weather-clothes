package com.weatherclothes.artist.data.mappers

import com.weatherclothes.artist.data.models_dto.ConditionDto
import com.weatherclothes.artist.domain.models.Condition
import javax.inject.Inject

class ConditionMapper @Inject constructor() {

    fun mapConditionToDomain(conditionDto: ConditionDto): Condition = with(conditionDto) {
        Condition(
            code = code
        )
    }
}