package com.weatherclothes.artist.utils

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
}