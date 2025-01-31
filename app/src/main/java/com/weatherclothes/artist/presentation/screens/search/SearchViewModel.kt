package com.weatherclothes.artist.presentation.screens.search

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weatherclothes.artist.domain.CurrentWeatherInteractor
import com.weatherclothes.artist.domain.SearchLocationInteractor
import com.weatherclothes.artist.domain.models.CurrentWeather
import com.weatherclothes.artist.domain.models.SearchLocation
import com.weatherclothes.artist.presentation.navigation.Navigator
import com.weatherclothes.artist.presentation.screens.main.KEY_SEX
import com.weatherclothes.artist.presentation.states.LocationState
import com.weatherclothes.artist.presentation.states.MainState
import com.weatherclothes.artist.utils.RecommendationClothes
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

private const val TAG = "MyLog"
class SearchViewModel @AssistedInject constructor(
    private val interactor: SearchLocationInteractor,
    private val weatherInteractor: CurrentWeatherInteractor,
    private val prefs: SharedPreferences,
    private val navigator: Navigator,
) : ViewModel() {

    var query: String = ""
    var places: MutableList<SearchLocation> = mutableListOf()
    var weather: CurrentWeather? = null
    var manImage: Int? = null
    var currentLocation: SearchLocation? = null

    private val _searchState = MutableStateFlow<MainState>(MainState.Success)
    val searchState = _searchState.asStateFlow()

    private val _placeState = MutableStateFlow<LocationState>(LocationState.Hiding)
    val placeState = _placeState.asStateFlow()

    fun getSearchLocation(nameLocation: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _searchState.value = MainState.Loading
            places = interactor.loadSearchLocation(nameLocation)
            _searchState.value = MainState.Success
        }
    }

    fun clickItem(location: SearchLocation) {
        currentLocation = location
        loadWeatherOfCurrentLocation(location.lat.toDouble(), location.lon.toDouble())
    }

    fun loadWeatherOfCurrentLocation(
        latitude: Double,
        longitude: Double
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _placeState.value = LocationState.Loading
            val sex = prefs.getBoolean(KEY_SEX, true)
            weather = weatherInteractor.loadWeatherOfCurrentLocation(
                latitude = latitude,
                longitude = longitude
            )
            weather?.let {
                manImage = RecommendationClothes.getClothes(it, sex)
            }
            _placeState.value = LocationState.Success
        }
    }

    fun setHidingState() {
        _placeState.value = LocationState.Hiding
    }

    fun navigateUp(destination: Int) {
        navigator.navigateToRoot(destination)
    }

    @AssistedFactory
    interface Factory {

        fun create(): SearchViewModel
    }
}