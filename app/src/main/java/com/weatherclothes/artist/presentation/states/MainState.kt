package com.weatherclothes.artist.presentation.states

sealed class MainState {
    data object Loading: MainState()
    data object Success: MainState()
}