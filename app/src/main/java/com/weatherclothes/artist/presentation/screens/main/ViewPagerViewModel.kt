package com.weatherclothes.artist.presentation.screens.main

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weatherclothes.artist.domain.CurrentWeatherInteractor
import com.weatherclothes.artist.domain.models.CurrentWeather
import com.weatherclothes.artist.presentation.states.MainState
import com.weatherclothes.artist.utils.RecommendationClothes
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ViewPagerViewModel @AssistedInject constructor(
    private val interactor: CurrentWeatherInteractor,
    private val prefs: SharedPreferences,
) : ViewModel() {

    var weather: CurrentWeather? = null
    var manImage: Int? = null

    var paramHour1 = 8
    var paramHour2 = 8
    var paramHour3 = 8

    var paramDay1 = 0
    var paramDay2 = 0
    var paramDay3 = 0

    private val _viewPagerMainState = MutableStateFlow<MainState>(MainState.Success)
    val viewPagerState = _viewPagerMainState.asStateFlow()

    fun loadWeatherOfCurrentLocation(
        latitude: Double,
        longitude: Double
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _viewPagerMainState.value = MainState.Loading
            val sex = prefs.getBoolean(KEY_SEX, true)
            weather = interactor.loadWeatherOfCurrentLocation(
                latitude = latitude,
                longitude = longitude
            )
            setParamHour(weather?.location?.localHour)
            weather?.let {
                manImage = RecommendationClothes.getClothes(it, sex)
            }
            _viewPagerMainState.value = MainState.Success
        }
    }

    fun changeSexImage(sex: Boolean) {
        weather?.let {
            manImage = RecommendationClothes.getClothes(it, sex)
        }
    }

    fun setLoadingState() {
        _viewPagerMainState.value = MainState.Loading
        _viewPagerMainState.value = MainState.Success
    }

    private fun setParamHour(currentHour: Int?) {
        when (currentHour) {
            in 8..12 -> {
                paramHour1 = 13
                paramHour2 = 18
                paramHour3 = 23
            }
            in 13..17 -> {
                paramHour1 = 18
                paramHour2 = 23
                paramHour3 = 8
                paramDay3 = 1
            }
            in 18..22 -> {
                paramHour1 = 23
                paramHour2 = 8
                paramHour3 = 13
                paramDay2 = 1
                paramDay3 = 1
            }
            23 -> {
                paramHour1 = 8
                paramHour2 = 13
                paramHour3 = 18
                paramDay1 = 1
                paramDay2 = 1
                paramDay3 = 1
            }
            else -> {
                paramHour1 = 8
                paramHour2 = 13
                paramHour3 = 18
            }
        }
    }

    @AssistedFactory
    interface Factory {

        fun create(): ViewPagerViewModel
    }
}