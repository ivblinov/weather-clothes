package com.weatherclothes.artist.utils

import com.weatherclothes.artist.R
import java.util.Calendar

object WeatherConditions {

    val weatherCodeDayMap = mapOf(
        1000 to "Sunny",
        1003 to "Partly cloudy",
        1006 to "Cloudy",
        1009 to "Overcast",
        1030 to "Mist",
        1063 to "Patchy rain possible",
        1066 to "Patchy snow possible",
        1069 to "Patchy sleet possible",
        1072 to "Patchy freezing drizzle possible",
        1087 to "Thundery outbreaks possible",
        1114 to "Blowing snow",
        1117 to "Blizzard",
        1135 to "Fog",
        1147 to "Freezing fog",
        1150 to "Patchy light drizzle",
        1153 to "Light drizzle",
        1168 to "Freezing drizzle",
        1171 to "Heavy freezing drizzle",
        1180 to "Patchy light rain",
        1183 to "Light rain",
        1186 to "Moderate rain at times",
        1189 to "Moderate rain",
        1192 to "Heavy rain at times",
        1195 to "Heavy rain",
        1198 to "Light freezing rain",
        1201 to "Moderate or heavy freezing rain",
        1204 to "Light sleet",
        1207 to "Moderate or heavy sleet",
        1210 to "Patchy light snow",
        1213 to "Light snow",
        1216 to "Patchy moderate snow",
        1219 to "Moderate snow",
        1222 to "Patchy heavy snow",
        1225 to "Heavy snow",
        1237 to "Ice pellets",
        1240 to "Light rain shower",
        1243 to "Moderate or heavy rain shower",
        1246 to "Torrential rain shower",
        1249 to "Light sleet showers",
        1252 to "Moderate or heavy sleet showers",
        1255 to "Light snow showers",
        1258 to "Moderate or heavy snow showers",
        1261 to "Light showers of ice pellets",
        1264 to "Moderate or heavy showers of ice pellets",
        1273 to "Patchy light rain with thunder",
        1276 to "Moderate or heavy rain with thunder",
        1279 to "Patchy light snow with thunder",
        1282 to "Moderate or heavy snow with thunder"
    )

    val weatherCodeNightMap = mapOf(
        1000 to "Clear"
    )

    private val bigIconMap = mapOf(
        1000 to R.drawable.ic_big_sunny,
        1003 to R.drawable.ic_big_partly_cloudy,
        1006 to R.drawable.ic_big_cloudy,
        1009 to R.drawable.ic_big_cloudy,
        1030 to R.drawable.ic_big_foggy,
        1063 to R.drawable.ic_big_drizzle,
        1066 to R.drawable.ic_big_snow,
        1069 to R.drawable.ic_big_rain_snow,
        1072 to R.drawable.ic_big_rain_snow,
        1087 to R.drawable.ic_big_thunder,
        1114 to R.drawable.ic_big_snow,
        1117 to R.drawable.ic_big_blizzard,
        1135 to R.drawable.ic_big_foggy,
        1147 to R.drawable.ic_big_foggy,
        1150 to R.drawable.ic_big_freezing_drizzle,
        1153 to R.drawable.ic_big_freezing_drizzle,
        1168 to R.drawable.ic_big_freezing_drizzle,
        1171 to R.drawable.ic_big_freezing_drizzle,
        1180 to R.drawable.ic_big_drizzle,
        1183 to R.drawable.ic_big_drizzle,
        1186 to R.drawable.ic_big_freezing_drizzle,
        1189 to R.drawable.ic_big_freezing_drizzle,
        1192 to R.drawable.ic_big_freezing_drizzle,
        1195 to R.drawable.ic_big_freezing_drizzle,
        1198 to R.drawable.ic_big_freezing_drizzle,
        1201 to R.drawable.ic_big_freezing_drizzle,
        1204 to R.drawable.ic_big_rain_snow,
        1207 to R.drawable.ic_big_rain_snow,
        1210 to R.drawable.ic_big_snow,
        1213 to R.drawable.ic_big_snow,
        1216 to R.drawable.ic_big_snow,
        1219 to R.drawable.ic_big_snow,
        1222 to R.drawable.ic_big_snow,
        1225 to R.drawable.ic_big_snow,
        1237 to R.drawable.ic_big_snow,
        1240 to R.drawable.ic_big_heavy_rain,
        1243 to R.drawable.ic_big_heavy_rain,
        1246 to R.drawable.ic_big_freezing_drizzle,
        1249 to R.drawable.ic_big_rain_snow,
        1252 to R.drawable.ic_big_rain_snow,
        1255 to R.drawable.ic_big_snow,
        1258 to R.drawable.ic_big_snow,
        1261 to R.drawable.ic_big_snow,
        1264 to R.drawable.ic_big_snow,
        1273 to R.drawable.ic_big_thunder,
        1276 to R.drawable.ic_big_thunder,
        1279 to R.drawable.ic_big_thunder,
        1282 to R.drawable.ic_big_thunder,
    )

    val bigIconNightMap = mapOf(
        1000 to R.drawable.ic_big_clear,
        1003 to R.drawable.ic_big_partly_cloudy_night,
        1006 to R.drawable.ic_big_cloudy_night,
    )

    private val smallIconMap = mapOf(
        1000 to R.drawable.ic_small_sunny,
        1003 to R.drawable.ic_small_partly_cloudy,
        1006 to R.drawable.ic_small_cloudy,
        1009 to R.drawable.ic_small_cloudy,
        1030 to R.drawable.ic_small_foggy,
        1063 to R.drawable.ic_small_drizzle,
        1066 to R.drawable.ic_small_snow,
        1069 to R.drawable.ic_small_rain_snow,
        1072 to R.drawable.ic_small_rain_snow,
        1087 to R.drawable.ic_small_thunder,
        1114 to R.drawable.ic_small_snow,
        1117 to R.drawable.ic_small_blizzard,
        1135 to R.drawable.ic_small_foggy,
        1147 to R.drawable.ic_small_foggy,
        1150 to R.drawable.ic_small_freezing_drizzle,
        1153 to R.drawable.ic_small_freezing_drizzle,
        1168 to R.drawable.ic_small_freezing_drizzle,
        1171 to R.drawable.ic_small_freezing_drizzle,
        1180 to R.drawable.ic_small_drizzle,
        1183 to R.drawable.ic_small_drizzle,
        1186 to R.drawable.ic_small_freezing_drizzle,
        1189 to R.drawable.ic_small_freezing_drizzle,
        1192 to R.drawable.ic_small_freezing_drizzle,
        1195 to R.drawable.ic_small_freezing_drizzle,
        1198 to R.drawable.ic_small_freezing_drizzle,
        1201 to R.drawable.ic_small_freezing_drizzle,
        1204 to R.drawable.ic_small_rain_snow,
        1207 to R.drawable.ic_small_rain_snow,
        1210 to R.drawable.ic_small_snow,
        1213 to R.drawable.ic_small_snow,
        1216 to R.drawable.ic_small_snow,
        1219 to R.drawable.ic_small_snow,
        1222 to R.drawable.ic_small_snow,
        1225 to R.drawable.ic_small_snow,
        1237 to R.drawable.ic_small_snow,
        1240 to R.drawable.ic_small_heavy_rain,
        1243 to R.drawable.ic_small_heavy_rain,
        1246 to R.drawable.ic_small_freezing_drizzle,
        1249 to R.drawable.ic_small_rain_snow,
        1252 to R.drawable.ic_small_rain_snow,
        1255 to R.drawable.ic_small_snow,
        1258 to R.drawable.ic_small_snow,
        1261 to R.drawable.ic_small_snow,
        1264 to R.drawable.ic_small_snow,
        1273 to R.drawable.ic_small_thunder,
        1276 to R.drawable.ic_small_thunder,
        1279 to R.drawable.ic_small_thunder,
        1282 to R.drawable.ic_small_thunder,
    )

    val smallIconNightMap = mapOf(
        1000 to R.drawable.ic_small_clear,
        1003 to R.drawable.ic_small_partly_cloudy_night,
        1006 to R.drawable.ic_small_cloudy_night,
    )

    fun getDescription(code: Int, time: Int): String? {
        return if (time !in 6.. 22 && weatherCodeNightMap.containsKey(code))
            weatherCodeNightMap[code]
        else
            weatherCodeDayMap[code]
    }

    fun getBigIcon(code: Int, time: Int): Int? {
        return if (time !in 6.. 22 && bigIconNightMap.containsKey(code))
            bigIconNightMap[code]
        else
            bigIconMap[code]
    }

    fun getSmallIcon(code: Int, time: String): Int? {
        val time = time.substring(11..12).toInt()
        return if (time !in 6.. 22 && smallIconNightMap.containsKey(code))
            smallIconNightMap[code]
        else
            smallIconMap[code]
    }

//    fun getCurrentHour(): Int {
//        val calendar = Calendar.getInstance()
//        val hourOfDay = calendar.get(Calendar.HOUR_OF_DAY)
//        return hourOfDay
//    }
}