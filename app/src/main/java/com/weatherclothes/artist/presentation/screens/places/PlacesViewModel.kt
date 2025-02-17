package com.weatherclothes.artist.presentation.screens.places

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.DiffUtil
import com.weatherclothes.artist.domain.AddLocationEntityInteractor
import com.weatherclothes.artist.domain.ClearTableInteractor
import com.weatherclothes.artist.domain.DeleteLocationInteractor
import com.weatherclothes.artist.domain.GetLocationsInteractor
import com.weatherclothes.artist.domain.PlacesCurrentWeatherInteractor
import com.weatherclothes.artist.domain.models.LocationEntity
import com.weatherclothes.artist.domain.models.PlacesCurrentWeather
import com.weatherclothes.artist.presentation.navigation.MainRouter
import com.weatherclothes.artist.presentation.screens.places.recyclerView.PlacesDiffUtilCallback
import com.weatherclothes.artist.presentation.states.MainState
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PlacesViewModel @AssistedInject constructor(
    private val interactor: GetLocationsInteractor,
    private val clearTableInteractor: ClearTableInteractor,
    private val addLocationEntityInteractor: AddLocationEntityInteractor,
    private val placesCurrentWeatherInteractor: PlacesCurrentWeatherInteractor,
    private val deleteInteractor: DeleteLocationInteractor,
    private val router: MainRouter,
) : ViewModel() {

    var locations: MutableList<LocationEntity> = mutableListOf()
    var newLocations: MutableList<LocationEntity> = mutableListOf()
    var weatherLocations: MutableList<PlacesCurrentWeather?> = mutableListOf()
    var newWeatherLocations: MutableList<PlacesCurrentWeather?> = mutableListOf()

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
            newLocations = interactor.getLocations().toMutableList()
            newLocations.forEach {
                getWeatherLocation(it)
            }
            _placeState.value = MainState.Success
        }
    }

    suspend fun getWeatherLocation(locationEntity: LocationEntity) {
        val cityWeather = placesCurrentWeatherInteractor.loadPlacesWeather(
            locationEntity.lat.toDouble(),
            locationEntity.lon.toDouble()
        )
        if (cityWeather == null) {
            setErrorState()
        } else {
            newWeatherLocations.add(cityWeather)
        }
    }

    fun itemMove(from: Int, to: Int) {
        val cities = locations.toMutableList()
        val moveCity = cities.removeAt(from)
        cities.add(to, moveCity)
        locations = cities
        viewModelScope.launch(Dispatchers.IO) {
            clearTableInteractor.clear()
            cities.forEach {
                addLocationEntityInteractor.addLocationEntity(it)
            }
        }
    }

    fun deleteLocation(position: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val location = locations.getOrNull(position)
            location?.let {
                deleteInteractor.deleteLocation(it)
                getLocations()
            }
        }
    }

    fun getDiffResult(): DiffUtil.DiffResult {
        val diffCallback = PlacesDiffUtilCallback(weatherLocations, newWeatherLocations)
        return DiffUtil.calculateDiff(diffCallback)
    }

    fun getNewWeatherLocationsList() = newWeatherLocations

    fun updateWeatherLocationsList() {
        weatherLocations = newWeatherLocations
        locations = newLocations
        newWeatherLocations = mutableListOf()
        newWeatherLocations = mutableListOf()
    }

    @AssistedFactory
    interface Factory {

        fun create(): PlacesViewModel
    }
}