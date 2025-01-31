package com.weatherclothes.artist.presentation.states

sealed class LocationState {
    data object Loading: LocationState()
    data object Success: LocationState()
    data object Hiding: LocationState()
}