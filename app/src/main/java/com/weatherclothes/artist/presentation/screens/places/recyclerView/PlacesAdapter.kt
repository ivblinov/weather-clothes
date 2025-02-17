package com.weatherclothes.artist.presentation.screens.places.recyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weatherclothes.artist.databinding.ItemPlacesBinding
import com.weatherclothes.artist.domain.models.PlacesCurrentWeather
import com.weatherclothes.artist.utils.WeatherConditions

class PlacesAdapter(
    private var placesList: MutableList<PlacesCurrentWeather?> = mutableListOf(),
    private val degrees: Boolean,
    private val itemSelectedColor: Int,
    private val colorBgSecondary: Int,
    private val onItemMoved: (Int, Int) -> Unit,
) : RecyclerView.Adapter<PlacesAdapter.ViewHolder>(), HelperAdapter {

    fun updateList(list: MutableList<PlacesCurrentWeather?>) {
        this.placesList = list
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder = ViewHolder(
        binding = getBinding(parent),
        degrees = degrees,
        itemSelectedColor = itemSelectedColor,
        colorBgSecondary = colorBgSecondary,
    )

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.binding.foreground.translationX = 0f
        val weather = placesList[position]
        holder.onBind(weather)
    }

    override fun getItemCount(): Int = placesList.size

    private fun getBinding(parent: ViewGroup): ItemPlacesBinding =
        ItemPlacesBinding.inflate(LayoutInflater.from(parent.context), parent, false)

    override fun itemMove(from: Int, to: Int) {
        onItemMoved.invoke(from, to)
    }

    class ViewHolder(
        val binding: ItemPlacesBinding,
        private val degrees: Boolean,
        private val itemSelectedColor: Int,
        private val colorBgSecondary: Int,
    ) : RecyclerView.ViewHolder(binding.root), HelperViewHolder {

        fun onBind(weather: PlacesCurrentWeather?) {

            weather?.let {
                binding.city.text = it.location.name

                val code = it.current.condition.code
                WeatherConditions.getSmallIconForItemPlaces(code)?.let {
                    binding.imageStatus.setImageResource(it)
                }

                var temperature = "${it.current.tempC}°"
                if (it.current.tempC > 0) temperature = "+$temperature"
                if (!degrees) {
                    temperature = "${it.current.tempF}°"
                    if (it.current.tempF > 0) temperature = "+$temperature"
                }
                binding.temperature.text = temperature
            }
        }

        override fun onItemSelected() {
            binding.foreground.setBackgroundResource(itemSelectedColor)
        }

        override fun onItemClear() {
            binding.foreground.setBackgroundResource(colorBgSecondary)
        }
    }
}