package com.weatherclothes.artist.presentation.screens.places

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weatherclothes.artist.domain.CurrentWeatherInteractor
import com.weatherclothes.artist.domain.GetLocationsInteractor
import com.weatherclothes.artist.domain.models.CurrentWeather
import com.weatherclothes.artist.domain.models.LocationEntity
import com.weatherclothes.artist.presentation.navigation.MainRouter
import com.weatherclothes.artist.presentation.states.MainState
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

private const val TAG = "MyLog"

class PlacesViewModel @AssistedInject constructor(
    private val interactor: GetLocationsInteractor,
    private val currentWeatherInteractor: CurrentWeatherInteractor,
    private val router: MainRouter,
) : ViewModel() {

    var locations: List<LocationEntity> = listOf()
    var weatherLocations: MutableList<CurrentWeather?> = mutableListOf()

    private val _placeState = MutableStateFlow<MainState>(MainState.Success)
    val placeState = _placeState.asStateFlow()

    fun openSearch() {
        viewModelScope.launch(Dispatchers.Main) {
            router.openSearchFragment()
        }
    }

    fun getLocations() {
        viewModelScope.launch(Dispatchers.IO) {
            _placeState.value = MainState.Loading
            Log.d(TAG, "getLocations: start")
            locations = interactor.getLocations()
            locations.forEach {
                getWeatherLocation(it)
            }
            _placeState.value = MainState.Success
            Log.d(TAG, "getLocations: end")
        }
    }

    suspend fun getWeatherLocation(locationEntity: LocationEntity) {
        weatherLocations.add(
            currentWeatherInteractor.loadWeatherOfCurrentLocation(
                locationEntity.lat.toDouble(),
                locationEntity.lon.toDouble()
            )
        )
    }

    @AssistedFactory
    interface Factory {

        fun create(): PlacesViewModel
    }
}