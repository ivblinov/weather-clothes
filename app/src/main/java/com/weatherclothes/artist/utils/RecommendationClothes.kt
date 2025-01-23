package com.weatherclothes.artist.utils

import android.util.Log
import com.weatherclothes.artist.R
import com.weatherclothes.artist.domain.models.CurrentWeather
import com.weatherclothes.artist.domain.models.Hour
import kotlin.math.abs

private const val TAG = "MyLog"
object RecommendationClothes {

    fun getClothes(weather: CurrentWeather): Int? {
        var codeValue = weather.current.condition.code
        val temperature = weather.current.tempC
        val feelsLikeTemp = weather.current.feelsLikeC
        val diffTemp = abs(feelsLikeTemp - temperature)
        var tempValue = if (diffTemp >= 3) feelsLikeTemp else temperature
        val time = weather.location.localHour
        val threeHourList = getThreeHourList(weather)

        val hourTempList = getThreeHourTempList(threeHourList)
        for (hourTemp in hourTempList) {
            if ((temperature - hourTemp) >= 3 || (hourTemp - temperature) >= 5) {
                tempValue = hourTemp
                break
            }
        }

        val sunrise = weather.forecast.forecastDay[0].astro.sunrise
        val sunset = weather.forecast.forecastDay[0].astro.sunset
        val hourCodeList = getThreeHourWeatherCodeList(threeHourList)
        codeValue = chooseSunOrRain(hourCodeList, codeValue)

        return when {
            checkCodeValue(codeValue) && tempValue >= 0 -> {
                getManRainCloudy(tempValue)
            }
            checkCodeSunValue(codeValue) && time in sunrise..sunset -> {
                getManSunCloudy(tempValue)
            }
            else -> {
                getManCloudy(tempValue)
            }
        }
    }

    private fun chooseSunOrRain(list: List<Int>, code: Int): Int {
        if (checkCodeValue(code)) {
            return code
        } else {
            for (hourCode in list) {
                if (checkCodeValue(hourCode))
                    return hourCode
            }
            for (hourCode in list) {
                if (checkCodeSunValue(hourCode))
                    return hourCode
            }
            return code
        }
    }

    private fun getThreeHourList(weather: CurrentWeather): List<Hour> {
        val hourList = weather.forecast.forecastDay[0].hour + weather.forecast.forecastDay[1].hour
        val currentHourIndex = hourList.indexOfFirst { hour ->
            hour.time.substring(11..12).toInt() == weather.location.localHour
        }
        return hourList.subList(currentHourIndex + 1, currentHourIndex + 4)
    }

    private fun getThreeHourTempList(hourList: List<Hour>): List<Int> {
        val tempList = mutableListOf<Int>()
        hourList.forEach { hour ->
            tempList.add(hour.tempC)
        }
        return tempList.toList()
    }

    private fun getThreeHourWeatherCodeList(hourList: List<Hour>): List<Int> {
        val codeList = mutableListOf<Int>()
        hourList.forEach { hour ->
            codeList.add(hour.condition.code)
        }
        Log.d(TAG, "getThreeHourWeatherCodeList: codeList = $codeList")
        return codeList.toList()
    }

    private val rainCodeArray = intArrayOf(
        1063,1066, 1069, 1072, 1087, 1114, 1117, 1150,
        1153, 1168, 1171, 1180, 1183, 1186, 1189, 1192,
        1195, 1198, 1201, 1204, 1207, 1210, 1213, 1216,
        1219, 1222, 1225, 1237, 1240, 1243, 1246, 1249,
        1252, 1255, 1258, 1261, 1264, 1273, 1276, 1279,
        1282,
    )

    private val sunCodeArray = intArrayOf(1000, 1003)

    private fun getManSunCloudy(tempValue: Int): Int {
        return when (tempValue) {
            in 25..70 -> R.drawable.man_sun_more_25
            in 20..24 -> R.drawable.man_sun_20_25
            in 15..19 -> R.drawable.man_sun_15_20
            in 10..14 -> R.drawable.man_sun_10_15
            in 0..9 -> R.drawable.man_sun_0_10
            in -10..-1 -> R.drawable.man_sun_minus_10_0
            else -> R.drawable.man_sun_minus_20_minus_10
        }
    }

    private fun getManRainCloudy(tempValue: Int): Int {
        return when (tempValue) {
            in 25..70 -> R.drawable.man_rain_more_25
            in 20..24 -> R.drawable.man_rain_20_25
            in 15..19 -> R.drawable.man_rain_15_20
            in 10..14 -> R.drawable.man_rain_10_15
            in 0..9 -> R.drawable.man_rain_0_10
            in -10..-1 -> R.drawable.man_minus_10_0
            else -> R.drawable.man_minus_20_minus_10
        }
    }

    private fun getManCloudy(tempValue: Int): Int {
        return when (tempValue) {
            in 25..70 -> R.drawable.man_more_25
            in 20..24 -> R.drawable.man_20_25
            in 15..19 -> R.drawable.man_15_20
            in 10..14 -> R.drawable.man_10_15
            in 0..9 -> R.drawable.man_0_10
            in -10..-1 -> R.drawable.man_minus_10_0
            else -> R.drawable.man_minus_20_minus_10
        }
    }

    private fun getWomanSunCloudy(tempValue: Int): Int {
        return when (tempValue) {
            in 25..70 -> R.drawable.woman_sun_more_25
            in 20..24 -> R.drawable.woman_sun_20_25
            in 15..19 -> R.drawable.woman_sun_15_20
            in 10..14 -> R.drawable.woman_sun_10_15
            in 0..9 -> R.drawable.woman_sun_0_10
            in -10..-1 -> R.drawable.woman_sun_minus_10_0
            else -> R.drawable.woman_sun_minus_20_minus_10
        }
    }

    private fun getWomanRainCloudy(tempValue: Int): Int {
        return when (tempValue) {
            in 25..70 -> R.drawable.woman_rain_more_25
            in 20..24 -> R.drawable.woman_rain_20_25
            in 15..19 -> R.drawable.woman_rain_15_20
            in 10..14 -> R.drawable.woman_rain_10_15
            in 0..9 -> R.drawable.woman_rain_0_10
            in -10..-1 -> R.drawable.woman_minus_10_0
            else -> R.drawable.woman_minus_20_minus_10
        }
    }

    private fun getWomanCloudy(tempValue: Int): Int {
        return when (tempValue) {
            in 25..70 -> R.drawable.woman_more_25
            in 20..24 -> R.drawable.woman_20_25
            in 15..19 -> R.drawable.woman_15_20
            in 10..14 -> R.drawable.woman_10_15
            in 0..9 -> R.drawable.woman_0_10
            in -10..-1 -> R.drawable.woman_minus_10_0
            else -> R.drawable.woman_minus_20_minus_10
        }
    }

    private fun checkCodeValue(codeValue: Int) = codeValue in rainCodeArray

    private fun checkCodeSunValue(codeValue: Int) = codeValue in sunCodeArray
}