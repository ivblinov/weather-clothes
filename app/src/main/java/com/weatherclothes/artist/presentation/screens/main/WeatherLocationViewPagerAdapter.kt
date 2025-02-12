package com.weatherclothes.artist.presentation.screens.main

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.weatherclothes.artist.domain.models.LocationEntity
import com.weatherclothes.artist.presentation.screens.permissions.PermissionsFragment

private const val TAG = "MyLog"
class WeatherLocationViewPagerAdapter(
    fragment: Fragment,
    private val location: MutableList<LocationEntity>?,
    private val permissionFlag: Boolean,
) : FragmentStateAdapter(fragment) {

    private var permissionsFlag: Boolean = true

    init {

//        Log.d(TAG, "createFragment: list = $location")
    }

    fun changeFlag(flag: Boolean) {
        permissionsFlag = flag
    }

    fun updateLocations(newLocations: MutableList<LocationEntity>) {
        location?.clear()
        location?.addAll(newLocations)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        var count = 1
        location?.size?.let {
            count += it
        }
        Log.d(TAG, "getItemCount: = $count")
        return count
    }

    override fun createFragment(position: Int): Fragment {
//        Log.d(TAG, "createFragment: position")
        return when {

            position == 0 && permissionsFlag -> {
                ViewPagerFragment()
            }

            position == 0 && !permissionsFlag -> {
                PermissionsFragment()
            }

            else -> {


                var locationEntity: LocationEntity? = null
                try {
                    locationEntity = location?.get(position - 1)
//                    Log.d(TAG, "createFragment: locationEntity = $locationEntity")
                } catch (_: IndexOutOfBoundsException) { }

                ViewPagerFragment.newInstance(locationEntity)
            }
        }
    }
}