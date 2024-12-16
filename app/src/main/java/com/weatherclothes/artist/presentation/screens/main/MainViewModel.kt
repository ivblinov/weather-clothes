package com.weatherclothes.artist.presentation.screens.main

import android.util.Log
import androidx.lifecycle.ViewModel
import com.weatherclothes.artist.presentation.states.MainState
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

private const val TAG = "MyLog"
class MainViewModel @AssistedInject constructor(

) : ViewModel() {

    private val _mainState = MutableStateFlow<MainState>(MainState.Success)
    val mainState = _mainState.asStateFlow()

    fun loadWeather() {
        _mainState.value = MainState.Loading
        Log.d(TAG, "loadWeather: ")
        _mainState.value = MainState.Success
    }

    @AssistedFactory
    interface Factory {

        fun create(): MainViewModel
    }
}