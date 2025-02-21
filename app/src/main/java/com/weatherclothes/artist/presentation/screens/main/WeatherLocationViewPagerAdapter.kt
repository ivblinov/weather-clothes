package com.weatherclothes.artist.presentation.screens.main

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.weatherclothes.artist.domain.models.LocationEntity
import com.weatherclothes.artist.presentation.screens.permissions.PermissionsFragment

class WeatherLocationViewPagerAdapter(
    fragment: Fragment,
    private var location: List<LocationEntity>?,
    private val permissionFlag: Boolean,
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        var count = 1
        location?.size?.let {
            count += it
        }
        return count
    }

    override fun createFragment(position: Int): Fragment {
        return when {

            position == 0 && permissionFlag -> {
                ViewPagerFragment()
            }

            position == 0 && !permissionFlag -> {
                PermissionsFragment()
            }

            else -> {
                var locationEntity: LocationEntity? = null
                try {
                    locationEntity = location?.get(position - 1)
                } catch (_: IndexOutOfBoundsException) { }

                ViewPagerFragment.newInstance(locationEntity, position)
            }
        }
    }
}