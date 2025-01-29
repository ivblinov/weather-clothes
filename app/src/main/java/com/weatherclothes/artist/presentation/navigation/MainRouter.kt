package com.weatherclothes.artist.presentation.navigation

interface MainRouter {

    fun closeCurrentFragment()

    fun openSearchFragment()

    fun navigateToRoot(destinationId: Int)
}