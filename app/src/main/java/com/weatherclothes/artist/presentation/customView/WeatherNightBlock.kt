package com.weatherclothes.artist.presentation.customView

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.weatherclothes.artist.R
import com.weatherclothes.artist.databinding.WeatherNightBlockLayoutBinding

class WeatherNightBlock @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private val binding: WeatherNightBlockLayoutBinding
    var timesOfDay: AppCompatTextView? = null
    var iconWeather: AppCompatImageView? = null
    var temperature: AppCompatTextView? = null

    init {
        val view = inflate(context, R.layout.weather_night_block_layout, this)
        binding = WeatherNightBlockLayoutBinding.bind(view)

        timesOfDay = binding.timesOfDay
        iconWeather = binding.iconWeather
        temperature = binding.temperature

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.WeatherNightBlock)
        val timesOfDay = typedArray.getString(R.styleable.WeatherNightBlock_timesOfDay)
        val iconWeather = typedArray.getDrawable(R.styleable.WeatherNightBlock_iconWeather)
        val temperature = typedArray.getString(R.styleable.WeatherNightBlock_temperature)

        binding.timesOfDay.text = timesOfDay
        binding.iconWeather.setImageDrawable(iconWeather)
        binding.temperature.text = temperature
        typedArray.recycle()
    }

    fun setTimesOfDay(timesOfDay: String) {
        binding.timesOfDay.text = timesOfDay
    }

    fun setIconWeather(resId: Drawable) {
        binding.iconWeather.setImageDrawable(resId)
    }

    fun setTemperature(temperature: String) {
        binding.temperature.text = temperature
    }
}