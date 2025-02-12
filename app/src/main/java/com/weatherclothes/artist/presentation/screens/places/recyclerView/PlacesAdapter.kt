package com.weatherclothes.artist.presentation.screens.places.recyclerView

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weatherclothes.artist.databinding.ItemPlacesBinding
import com.weatherclothes.artist.domain.models.PlacesCurrentWeather
import com.weatherclothes.artist.utils.WeatherConditions

private const val TAG = "MyLog"

class PlacesAdapter(
    private var placesList: MutableList<PlacesCurrentWeather?> = mutableListOf(),
    private val degrees: Boolean,
    private val itemSelectedColor: Int,
    private val colorBgSecondary: Int,
    private val onItemMoved: (Int, Int) -> Unit,
) : RecyclerView.Adapter<PlacesAdapter.ViewHolder>(), HelperAdapter {

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
        itemSelectedColor = itemSelectedColor,
        colorBgSecondary = colorBgSecondary,
    )

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val weather = placesList[position]
        holder.onBind(weather)
        holder.binding.iconDelete.apply {
            isClickable = true
            isFocusable = true
            bringToFront()
            setOnClickListener {
                Log.d(TAG, "onDelete")
            }
        }
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