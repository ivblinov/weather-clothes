package com.weatherclothes.artist.presentation.screens.main

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.weatherclothes.artist.presentation.screens.permissions.PermissionsFragment

class WeatherLocationViewPagerAdapter(
    fragmentActivity: FragmentActivity,
    private val fragments: MutableList<Fragment>,
    private val titles: MutableList<String>,
) : FragmentStateAdapter(fragmentActivity) {

//    @SuppressLint("NotifyDataSetChanged")
//    fun updateData(newFragments: List<Fragment>, newTitles: List<String>) {
//        fragments.clear()
//        fragments.addAll(newFragments)
//
//        titles.clear()
//        titles.addAll(newTitles)
//
//        notifyDataSetChanged()
//    }

    override fun getItemCount() = fragments.size

    override fun createFragment(position: Int) = fragments[position]

    fun getTitle(position: Int): String = titles[position]
}