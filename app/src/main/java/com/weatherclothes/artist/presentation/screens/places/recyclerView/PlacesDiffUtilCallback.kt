package com.weatherclothes.artist.presentation.screens.places.recyclerView

import androidx.recyclerview.widget.DiffUtil
import com.weatherclothes.artist.domain.models.PlacesCurrentWeather

class PlacesDiffUtilCallback(
    private val oldList: MutableList<PlacesCurrentWeather?>,
    private val newList: MutableList<PlacesCurrentWeather?>,
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int =
        oldList.size

    override fun getNewListSize(): Int =
        newList.size

    override fun areItemsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Boolean {
        return oldList[oldItemPosition]?.location == newList[newItemPosition]?.location
    }

    override fun areContentsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}