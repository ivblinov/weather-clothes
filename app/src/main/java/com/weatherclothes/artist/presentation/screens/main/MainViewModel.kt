package com.weatherclothes.artist.presentation.screens.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weatherclothes.artist.domain.CurrentWeatherInteractor
import com.weatherclothes.artist.presentation.states.MainState
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val TAG = "MyLog"

class MainViewModel @AssistedInject constructor(
    private val interactor: CurrentWeatherInteractor
) : ViewModel() {

    private val _mainState = MutableStateFlow<MainState>(MainState.Success)
    val mainState = _mainState.asStateFlow()

    fun loadWeatherOfCurrentLocation(
        latitude: Double,
        longitude: Double
    ) {
        Log.d(TAG, "loadWeatherOfCurrentLocation: ")
        viewModelScope.launch(Dispatchers.IO) {
            _mainState.value = MainState.Loading
            Log.d(TAG, "currentResult")
//            val currentResult = interactor.loadWeatherOfCurrentLocation(
//                latitude = latitude,
//                longitude = longitude
//            )
//            Log.d(TAG, "currentResult = $currentResult")
            _mainState.value = MainState.Success
        }
    }

    @AssistedFactory
    interface Factory {

        fun create(): MainViewModel
    }
}