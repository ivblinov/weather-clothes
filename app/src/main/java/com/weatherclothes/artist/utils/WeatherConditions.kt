package com.weatherclothes.artist.utils

import com.weatherclothes.artist.R

object WeatherConditions {

    private val weatherCodeDayMap = mapOf(
        1000 to "Sunny",                    // sun
        1003 to "Partly cloudy",            // sun (переменная облачность)
        1006 to "Cloudy",                           // cloudy
        1009 to "Overcast",                         // cloudy
        1030 to "Mist",                             // cloudy
        1063 to "Patchy rain possible",                         // rain
        1066 to "Patchy snow possible",                         // rain
        1069 to "Patchy sleet possible",                        // rain (возможен мокрый снег)
        1072 to "Patchy freezing drizzle possible",             // rain (Возможны кратковременные замерзающие моросящие дожди)
        1087 to "Thundery outbreaks possible",                  // rain (Возможны грозовые вспышки)
        1114 to "Blowing snow",                                 // rain (Снежная метель)
        1117 to "Blizzard",                                     // rain (метель)
        1135 to "Fog",                              // cloudy
        1147 to "Freezing fog",                     // cloudy (Замерзающий туман)
        1150 to "Patchy light drizzle",                         // rain (Местами моросящий дождь)
        1153 to "Light drizzle",                                // rain (Легкий моросящий дождь)
        1168 to "Freezing drizzle",                             // rain (Замерзающая морось)
        1171 to "Heavy freezing drizzle",                       // rain (Сильная ледяная морось)
        1180 to "Patchy light rain",                            // rain (Местами небольшой дождь)
        1183 to "Light rain",                                   // rain (небольшой дождь)
        1186 to "Moderate rain at times",                       // rain (Временами умеренный дождь)
        1189 to "Moderate rain",                                // rain (Умеренный дождь)
        1192 to "Heavy rain at times",                          // rain (Временами сильный дождь)
        1195 to "Heavy rain",                                   // rain
        1198 to "Light freezing rain",                          // rain (Небольшой ледяной дождь)
        1201 to "Moderate or heavy freezing rain",              // rain (Умеренный или сильный замерзающий дождь)
        1204 to "Light sleet",                                  // rain (небольшой мокрый снег)
        1207 to "Moderate or heavy sleet",                      // rain (Умеренный или сильный мокрый снег)
        1210 to "Patchy light snow",                            // rain (Местами небольшой снег)
        1213 to "Light snow",                                   // rain (Небольшой снег)
        1216 to "Patchy moderate snow",                         // rain (Местами умеренный снег)
        1219 to "Moderate snow",                                // rain (Умеренный снег)
        1222 to "Patchy heavy snow",                            // rain (Местами сильный снег)
        1225 to "Heavy snow",                                   // rain (Сильный снегопад)
        1237 to "Ice pellets",                                  // rain (Ледяная крупа)
        1240 to "Light rain shower",                            // rain (Небольшой дождь)
        1243 to "Moderate or heavy rain shower",                // rain (Умеренный или сильный ливневый дождь)
        1246 to "Torrential rain shower",                       // rain (Проливной дождь)
        1249 to "Light sleet showers",                          // rain (Небольшие ливневые дожди со снегом)
        1252 to "Moderate or heavy sleet showers",              // rain (Умеренные или сильные ливневые дожди со снегом)
        1255 to "Light snow showers",                           // rain (Небольшие ливневые снегопады)
        1258 to "Moderate or heavy snow showers",               // rain (Умеренные или сильные снегопады)
        1261 to "Light showers of ice pellets",                 // rain (Слабые ливни с ледяной крупой)
        1264 to "Moderate or heavy showers of ice pellets",     // rain (Умеренные или сильные ливни с ледяной крупой)
        1273 to "Patchy light rain with thunder",               // rain (Местами небольшой дождь с грозой)
        1276 to "Moderate or heavy rain with thunder",          // rain (Умеренный или сильный дождь с грозой)
        1279 to "Patchy light snow with thunder",               // rain (Местами небольшой снег с грозой)
        1282 to "Moderate or heavy snow with thunder"           // rain (Умеренный или сильный снег с грозой)
    )

    private val weatherCodeNightMap = mapOf(1000 to "Clear")

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

    private val bigIconMapWhenCold = mapOf(
        1000 to R.drawable.ic_big_sunny,
        1003 to R.drawable.ic_big_partly_cloudy,
        1006 to R.drawable.ic_big_cloudy,
        1009 to R.drawable.ic_big_cloudy,
        1030 to R.drawable.ic_big_foggy,
        1063 to R.drawable.ic_big_snow,
        1066 to R.drawable.ic_big_snow,
        1069 to R.drawable.ic_big_snow,
        1072 to R.drawable.ic_big_snow,
        1087 to R.drawable.ic_big_thunder,
        1114 to R.drawable.ic_big_snow,
        1117 to R.drawable.ic_big_blizzard,
        1135 to R.drawable.ic_big_foggy,
        1147 to R.drawable.ic_big_foggy,
        1150 to R.drawable.ic_big_snow,
        1153 to R.drawable.ic_big_snow,
        1168 to R.drawable.ic_big_snow,
        1171 to R.drawable.ic_big_snow,
        1180 to R.drawable.ic_big_snow,
        1183 to R.drawable.ic_big_snow,
        1186 to R.drawable.ic_big_snow,
        1189 to R.drawable.ic_big_snow,
        1192 to R.drawable.ic_big_snow,
        1195 to R.drawable.ic_big_snow,
        1198 to R.drawable.ic_big_snow,
        1201 to R.drawable.ic_big_snow,
        1204 to R.drawable.ic_big_snow,
        1207 to R.drawable.ic_big_snow,
        1210 to R.drawable.ic_big_snow,
        1213 to R.drawable.ic_big_snow,
        1216 to R.drawable.ic_big_snow,
        1219 to R.drawable.ic_big_snow,
        1222 to R.drawable.ic_big_snow,
        1225 to R.drawable.ic_big_snow,
        1237 to R.drawable.ic_big_snow,
        1240 to R.drawable.ic_big_snow,
        1243 to R.drawable.ic_big_snow,
        1246 to R.drawable.ic_big_snow,
        1249 to R.drawable.ic_big_snow,
        1252 to R.drawable.ic_big_snow,
        1255 to R.drawable.ic_big_snow,
        1258 to R.drawable.ic_big_snow,
        1261 to R.drawable.ic_big_snow,
        1264 to R.drawable.ic_big_snow,
        1273 to R.drawable.ic_big_thunder,
        1276 to R.drawable.ic_big_thunder,
        1279 to R.drawable.ic_big_thunder,
        1282 to R.drawable.ic_big_thunder,
    )

    private val bigIconNightMap = mapOf(
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

    private val smallIconMapWhenCold = mapOf(
        1000 to R.drawable.ic_small_sunny,
        1003 to R.drawable.ic_small_partly_cloudy,
        1006 to R.drawable.ic_small_cloudy,
        1009 to R.drawable.ic_small_cloudy,
        1030 to R.drawable.ic_small_foggy,
        1063 to R.drawable.ic_small_snow,
        1066 to R.drawable.ic_small_snow,
        1069 to R.drawable.ic_small_snow,
        1072 to R.drawable.ic_small_snow,
        1087 to R.drawable.ic_small_thunder,
        1114 to R.drawable.ic_small_snow,
        1117 to R.drawable.ic_small_blizzard,
        1135 to R.drawable.ic_small_foggy,
        1147 to R.drawable.ic_small_foggy,
        1150 to R.drawable.ic_small_snow,
        1153 to R.drawable.ic_small_snow,
        1168 to R.drawable.ic_small_snow,
        1171 to R.drawable.ic_small_snow,
        1180 to R.drawable.ic_small_snow,
        1183 to R.drawable.ic_small_snow,
        1186 to R.drawable.ic_small_snow,
        1189 to R.drawable.ic_small_snow,
        1192 to R.drawable.ic_small_snow,
        1195 to R.drawable.ic_small_snow,
        1198 to R.drawable.ic_small_snow,
        1201 to R.drawable.ic_small_snow,
        1204 to R.drawable.ic_small_snow,
        1207 to R.drawable.ic_small_snow,
        1210 to R.drawable.ic_small_snow,
        1213 to R.drawable.ic_small_snow,
        1216 to R.drawable.ic_small_snow,
        1219 to R.drawable.ic_small_snow,
        1222 to R.drawable.ic_small_snow,
        1225 to R.drawable.ic_small_snow,
        1237 to R.drawable.ic_small_snow,
        1240 to R.drawable.ic_small_snow,
        1243 to R.drawable.ic_small_snow,
        1246 to R.drawable.ic_small_snow,
        1249 to R.drawable.ic_small_snow,
        1252 to R.drawable.ic_small_snow,
        1255 to R.drawable.ic_small_snow,
        1258 to R.drawable.ic_small_snow,
        1261 to R.drawable.ic_small_snow,
        1264 to R.drawable.ic_small_snow,
        1273 to R.drawable.ic_small_thunder,
        1276 to R.drawable.ic_small_thunder,
        1279 to R.drawable.ic_small_thunder,
        1282 to R.drawable.ic_small_thunder,
    )

    private val smallIconNightMap = mapOf(
        1000 to R.drawable.ic_small_clear,
        1003 to R.drawable.ic_small_partly_cloudy_night,
        1006 to R.drawable.ic_small_cloudy_night,
    )

    fun getDescription(code: Int, time: Int, sunrise: Int, sunset: Int): String? {
        return if (time !in sunrise.. sunset && weatherCodeNightMap.containsKey(code))
            weatherCodeNightMap[code]
        else
            weatherCodeDayMap[code]
    }

    fun getBigIcon(code: Int, time: Int, temp: Int, sunrise: Int, sunset: Int): Int? {
        return when {
            time !in sunrise.. sunset && bigIconNightMap.containsKey(code) -> bigIconNightMap[code]
            temp <= -3 -> bigIconMapWhenCold[code]
            else -> bigIconMap[code]
        }
    }

    fun getSmallIcon(code: Int, time: String, temp: Int, sunrise: Int, sunset: Int): Int? {
        val time = time.substring(11..12).toInt()
        return when {
            time !in sunrise.. sunset && smallIconNightMap.containsKey(code) -> smallIconNightMap[code]
            temp <= -3 -> smallIconMapWhenCold[code]
            else -> smallIconMap[code]
        }
    }
}