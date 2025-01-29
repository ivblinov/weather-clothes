package com.weatherclothes.artist.presentation.navigation

import androidx.navigation.NavController
import com.weatherclothes.artist.R

class Navigator : MainRouter {

    private var navController: NavController? = null

    fun attachNavController(navController: NavController, graph: Int) {
        navController.setGraph(graph)
        this.navController = navController
    }

    fun detachNavController(navController: NavController) {
        if (this.navController == navController) {
            this.navController = null
        }
    }

    private fun canNavigate(startDestination: Int, endDestination: Int)
            = navController?.currentDestination?.id == startDestination
            && navController?.currentDestination?.id != endDestination

    override fun closeCurrentFragment() {
        if (navController?.currentDestination == null)
            return
        navController?.popBackStack()
    }

    override fun openSearchFragment() {
        if (!canNavigate(R.id.nav_places, R.id.searchFragment))
            return
        navController?.navigate(R.id.action_nav_places_to_searchFragment)
    }

    override fun navigateToRoot(destinationId: Int) {
        navController?.let {
            it.popBackStack(it.graph.startDestinationId, false)
            if (it.currentDestination?.id != destinationId) {
                it.navigate(destinationId)
            }
        }
    }
}