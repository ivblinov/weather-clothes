package com.weatherclothes.artist.presentation.screens.places

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weatherclothes.artist.domain.AddLocationEntityInteractor
import com.weatherclothes.artist.domain.ClearTableInteractor
import com.weatherclothes.artist.domain.CurrentWeatherInteractor
import com.weatherclothes.artist.domain.GetLocationsInteractor
import com.weatherclothes.artist.domain.PlacesCurrentWeatherInteractor
import com.weatherclothes.artist.domain.models.LocationEntity
import com.weatherclothes.artist.domain.models.PlacesCurrentWeather
import com.weatherclothes.artist.presentation.navigation.MainRouter
import com.weatherclothes.artist.presentation.states.MainState
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val TAG = "MyLog"

class PlacesViewModel @AssistedInject constructor(
    private val interactor: GetLocationsInteractor,
    private val currentWeatherInteractor: CurrentWeatherInteractor,
    private val clearTableInteractor: ClearTableInteractor,
    private val addLocationEntityInteractor: AddLocationEntityInteractor,
    private val placesCurrentWeatherInteractor: PlacesCurrentWeatherInteractor,
    private val router: MainRouter,
) : ViewModel() {

    var locations: List<LocationEntity> = listOf()
    var weatherLocations: MutableList<PlacesCurrentWeather?> = mutableListOf()

    private val _placeState = MutableStateFlow<MainState>(MainState.Success)
    val placeState = _placeState.asStateFlow()

    fun openSearch() {
        viewModelScope.launch(Dispatchers.Main) {
            router.openSearchFragment()
        }
    }

    fun setErrorState() {
        _placeState.value = MainState.Error
    }

    fun setUpdateState() {
        _placeState.value = MainState.Update
    }

    fun getLocations() {
        viewModelScope.launch(Dispatchers.IO) {
            _placeState.value = MainState.Loading
            locations = interactor.getLocations()
            locations.forEach {
                getWeatherLocation(it)
            }
            _placeState.value = MainState.Success
        }
    }

    suspend fun getWeatherLocation(locationEntity: LocationEntity) {
        weatherLocations.add(
            placesCurrentWeatherInteractor.loadPlacesWeather(
                locationEntity.lat.toDouble(),
                locationEntity.lon.toDouble()
            )
        )
    }

    fun itemMove(from: Int, to: Int) {
        val cities = locations.toMutableList()
        val moveCity = cities.removeAt(from)
        cities.add(to, moveCity)
        locations = cities.toList()
        viewModelScope.launch(Dispatchers.IO) {
            clearTableInteractor.clear()
            cities.forEach {
                addLocationEntityInteractor.addLocationEntity(it)
            }
        }
    }

    @AssistedFactory
    interface Factory {

        fun create(): PlacesViewModel
    }
}