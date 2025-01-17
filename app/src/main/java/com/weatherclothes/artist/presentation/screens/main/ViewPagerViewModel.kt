package com.weatherclothes.artist.presentation.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weatherclothes.artist.domain.CurrentWeatherInteractor
import com.weatherclothes.artist.domain.models.CurrentWeather
import com.weatherclothes.artist.presentation.states.MainState
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ViewPagerViewModel @AssistedInject constructor(
    private val interactor: CurrentWeatherInteractor
) : ViewModel() {

    var weather: CurrentWeather? = null

    private val _viewPagerMainState = MutableStateFlow<MainState>(MainState.Success)
    val viewPagerState = _viewPagerMainState.asStateFlow()

    fun loadWeatherOfCurrentLocation(
        latitude: Double,
        longitude: Double
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _viewPagerMainState.value = MainState.Loading
            weather = interactor.loadWeatherOfCurrentLocation(
                latitude = latitude,
                longitude = longitude
            )
            _viewPagerMainState.value = MainState.Success
        }
    }

    @AssistedFactory
    interface Factory {

        fun create(): ViewPagerViewModel
    }
}