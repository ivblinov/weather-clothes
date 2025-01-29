package com.weatherclothes.artist.presentation.screens.places

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weatherclothes.artist.presentation.navigation.MainRouter
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlacesViewModel @AssistedInject constructor(
    private val router: MainRouter,
) : ViewModel() {

    fun openSearch() {
        viewModelScope.launch(Dispatchers.Main) {
            router.openSearchFragment()
        }
    }

    @AssistedFactory
    interface Factory {

        fun create(): PlacesViewModel
    }
}