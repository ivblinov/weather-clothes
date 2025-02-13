package com.weatherclothes.artist.presentation.screens.main

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.weatherclothes.artist.domain.models.LocationEntity
import com.weatherclothes.artist.presentation.screens.permissions.PermissionsFragment

private const val TAG = "MyLog"
class WeatherLocationViewPagerAdapter(
    fragment: Fragment,
    private var location: List<LocationEntity>?,
    private val permissionFlag: Boolean,
) : FragmentStateAdapter(fragment) {

    private var permissionsFlag: Boolean = true

    init {

//        Log.d(TAG, "createFragment: list = $location")
    }

    fun changeFlag(flag: Boolean) {
        permissionsFlag = flag
    }

//    fun updateLocations(newLocations: MutableList<LocationEntity>) {
//        location?.clear()
//        location?.addAll(newLocations)
//        notifyDataSetChanged()
//    }

    override fun getItemCount(): Int {
        var count = 1
        location?.size?.let {
            count += it
        }
        return count
    }

    override fun createFragment(position: Int): Fragment {
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

                ViewPagerFragment.newInstance(locationEntity, position)
            }
        }
    }

//    override fun getItemId(position: Int): Long {
//        Log.d(TAG, "getItemId: ")
//        return if (position == 0)
//            10000000.hashCode().toLong()
//        else
//            location?.get(position - 1)?.id?.plus(position).hashCode().toLong()
//    }
//
//    override fun containsItem(itemId: Long): Boolean {
//        Log.d(TAG, "containsItem: ")
//        return location?.any { it.id.hashCode().toLong() == itemId } == true
//    }

//    fun updateItems(newItems: List<LocationEntity>?) {
//        location = newItems
//        notifyDataSetChanged()
//    }
}