package com.weatherclothes.artist.presentation.screens.search.recyclerView

import android.annotation.SuppressLint
import android.text.Spannable
import android.text.SpannableString
import android.text.style.TextAppearanceSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weatherclothes.artist.databinding.ItemSearchBinding
import com.weatherclothes.artist.domain.models.SearchLocation

class SearchAdapter(
    private var query: String = "",
    private var placesList: MutableList<SearchLocation> = mutableListOf(),
    private val styleBold: TextAppearanceSpan?,
    private val onClickItem: (SearchLocation) -> Unit,
) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun setList(query: String, list: MutableList<SearchLocation>) {
        this.placesList = list
        this.query = query
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder = ViewHolder(
        binding = getBinding(parent),
        onClickItem = onClickItem
    )

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val location = placesList[position]
        val locationFullName = if (location.name == location.region || location.region == "")
            "${location.name}, ${location.country}"
        else
            "${location.name}, ${location.region} region, ${location.country}"

        holder.textView.text = formatCityName(locationFullName, query)
        holder.onBind(location)
    }

    override fun getItemCount(): Int = placesList.size

    private fun getBinding(parent: ViewGroup): ItemSearchBinding =
        ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)

    class ViewHolder(
        val binding: ItemSearchBinding,
        private val onClickItem: (SearchLocation) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {

        val textView = binding.locationTV

        fun onBind(searchLocation: SearchLocation) {

            binding.locationTV.setOnClickListener {
                onClickItem.invoke(searchLocation)
            }
        }
    }

    private fun formatCityName(cityName: String, query: String): SpannableString {
        val spannable = SpannableString(cityName)
        val startIndex = cityName.lowercase().indexOf(query.lowercase())
        if (startIndex != -1) {
            val endIndex = startIndex + query.length
            spannable.setSpan(
                styleBold,
                startIndex,
                endIndex,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        return spannable
    }
}