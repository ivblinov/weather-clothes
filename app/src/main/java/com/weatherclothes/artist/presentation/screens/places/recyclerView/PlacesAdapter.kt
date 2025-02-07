package com.weatherclothes.artist.presentation.screens.places.recyclerView

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weatherclothes.artist.databinding.ItemPlacesBinding
import com.weatherclothes.artist.domain.models.PlacesCurrentWeather
import com.weatherclothes.artist.utils.WeatherConditions

class PlacesAdapter(
    private var placesList: MutableList<PlacesCurrentWeather?> = mutableListOf(),
    private val degrees: Boolean,
) : RecyclerView.Adapter<PlacesAdapter.ViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: MutableList<PlacesCurrentWeather?>) {
        this.placesList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder = ViewHolder(
        binding = getBinding(parent),
        degrees = degrees,
    )

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val weather = placesList[position]
        holder.onBind(weather)
    }

    override fun getItemCount(): Int = placesList.size

    private fun getBinding(parent: ViewGroup): ItemPlacesBinding =
        ItemPlacesBinding.inflate(LayoutInflater.from(parent.context), parent, false)

    class ViewHolder(
        val binding: ItemPlacesBinding,
        private val degrees: Boolean,
    ) : RecyclerView.ViewHolder(binding.root) {

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
    }
}