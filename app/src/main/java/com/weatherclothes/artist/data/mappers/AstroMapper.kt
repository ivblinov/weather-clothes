package com.weatherclothes.artist.data.mappers

import com.weatherclothes.artist.data.models_dto.AstroDto
import com.weatherclothes.artist.domain.models.Astro
import javax.inject.Inject

class AstroMapper @Inject constructor() {

    fun mapAstroToDomain(astroDto: AstroDto): Astro {
        val sunrise = astroDto.sunrise.substring(0..1).toInt()
        val sunset = astroDto.sunset.substring(0..1).toInt() + 12
        return Astro(sunrise = sunrise, sunset = sunset)
    }
}