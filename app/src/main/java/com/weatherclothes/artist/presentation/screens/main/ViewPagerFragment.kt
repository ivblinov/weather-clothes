package com.weatherclothes.artist.presentation.screens.main

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.weatherclothes.artist.R
import com.weatherclothes.artist.databinding.FragmentViewPagerBinding
import com.weatherclothes.artist.domain.models.CurrentWeather
import com.weatherclothes.artist.domain.models.LocationEntity
import com.weatherclothes.artist.presentation.states.MainState
import com.weatherclothes.artist.utils.MainViewModelFactory
import com.weatherclothes.artist.utils.WeatherConditions
import com.weatherclothes.artist.utils.appComponent
import com.weatherclothes.artist.utils.isInternetAvailable
import com.weatherclothes.artist.utils.lazyViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.getValue

private const val TAG = "MyLog"
class ViewPagerFragment : Fragment() {

    private var _binding: FragmentViewPagerBinding? = null
    private val binding get() = _binding!!

    private var mainViewModel: MainViewModel? = null

    private var city: LocationEntity? = null
    private var position = 0

    val viewModel: ViewPagerViewModel by lazyViewModel {
        requireContext().appComponent().viewPagerViewModel().create()
    }

    @Inject
    lateinit var prefs: SharedPreferences

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory

    override fun onAttach(context: Context) {
        super.onAttach(context)
        inject()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        city = arguments?.getParcelable(LOCATION_KEY)
        position = arguments?.getInt(POSITION_KEY) ?: 0
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mainViewModel =
            ViewModelProvider(requireActivity(), mainViewModelFactory)[MainViewModel::class.java]

        checkCurrentCity(position)

        city?.let {
            if (isInternetAvailable(requireContext()))
                viewModel.loadWeatherOfCurrentLocation(it.lat.toDouble(), it.lon.toDouble())
            else mainViewModel?.changeError()
        }
        _binding = FragmentViewPagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribe()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun inject() {
        requireContext().appComponent().inject(this)
    }

    private fun subscribe() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                launch {
                    mainViewModel?.let { vm ->
                        vm.mainState.collect { state ->
                            when (state) {
                                MainState.Loading -> {}
                                MainState.Success -> {
                                    if (city == null) {
                                        vm.weather?.let { setCurrentWeather(it) }
                                        setHourWeather()
                                        setManImage()
                                    }
                                }
                                MainState.Error -> {}
                                MainState.Update -> {}
                            }
                        }
                    }
                }

                launch {
                    viewModel.viewPagerState.collect { state ->
                        when (state) {
                            MainState.Error -> {}
                            MainState.Loading -> {}
                            MainState.Success -> {
                                if (city != null) {
                                    viewModel.weather?.let { setCurrentWeather(it) }
                                    setHourWeatherViewPagerState()
                                    setManImageViewPagerState()
                                }
                            }
                            MainState.Update -> {}
                        }
                    }
                }
            }
        }
    }

    private fun setManImage() {
        mainViewModel?.manImage?.let {
            binding.man.setImageResource(it)
            binding.man.visibility = View.VISIBLE
        }
    }

    private fun setManImageViewPagerState() {
        viewModel.manImage?.let {
            binding.man.setImageResource(it)
            binding.man.visibility = View.VISIBLE
        }
    }

    private fun setHourWeather() {
        mainViewModel?.let { vm ->
            vm.weather?.let { weather ->
                setTemperature(
                    binding.weatherNightBlock1.temperature,
                    weather,
                    vm.paramDay1,
                    vm.paramHour1
                )
                setTemperature(
                    binding.weatherNightBlock2.temperature,
                    weather,
                    vm.paramDay2,
                    vm.paramHour2
                )
                setTemperature(
                    binding.weatherNightBlock3.temperature,
                    weather,
                    vm.paramDay3,
                    vm.paramHour3
                )
                setSmallIconWeather(
                    binding.weatherNightBlock1.iconWeather,
                    weather,
                    vm.paramDay1,
                    vm.paramHour1
                )
                setSmallIconWeather(
                    binding.weatherNightBlock2.iconWeather,
                    weather,
                    vm.paramDay2,
                    vm.paramHour2
                )
                setSmallIconWeather(
                    binding.weatherNightBlock3.iconWeather,
                    weather,
                    vm.paramDay3,
                    vm.paramHour3
                )
                binding.timesOfDay.visibility = View.VISIBLE
            }
            binding.weatherNightBlock1.timesOfDay?.text = setTimesOfDay(vm.paramHour1)
            binding.weatherNightBlock2.timesOfDay?.text = setTimesOfDay(vm.paramHour2)
            binding.weatherNightBlock3.timesOfDay?.text = setTimesOfDay(vm.paramHour3)
        }
    }

    private fun setHourWeatherViewPagerState() {
        viewModel.let { vm ->
            vm.weather?.let { weather ->
                setTemperature(
                    binding.weatherNightBlock1.temperature,
                    weather,
                    vm.paramDay1,
                    vm.paramHour1
                )
                setTemperature(
                    binding.weatherNightBlock2.temperature,
                    weather,
                    vm.paramDay2,
                    vm.paramHour2
                )
                setTemperature(
                    binding.weatherNightBlock3.temperature,
                    weather,
                    vm.paramDay3,
                    vm.paramHour3
                )
                setSmallIconWeather(
                    binding.weatherNightBlock1.iconWeather,
                    weather,
                    vm.paramDay1,
                    vm.paramHour1
                )
                setSmallIconWeather(
                    binding.weatherNightBlock2.iconWeather,
                    weather,
                    vm.paramDay2,
                    vm.paramHour2
                )
                setSmallIconWeather(
                    binding.weatherNightBlock3.iconWeather,
                    weather,
                    vm.paramDay3,
                    vm.paramHour3
                )
                binding.timesOfDay.visibility = View.VISIBLE
            }
            binding.weatherNightBlock1.timesOfDay?.text = setTimesOfDay(vm.paramHour1)
            binding.weatherNightBlock2.timesOfDay?.text = setTimesOfDay(vm.paramHour2)
            binding.weatherNightBlock3.timesOfDay?.text = setTimesOfDay(vm.paramHour3)
        }
    }

    private fun setCurrentWeather(weather: CurrentWeather) {
        val degrees = prefs.getBoolean(KEY_DEGREES, true)
        var temperature = "${weather.current.tempC}°"
        if (weather.current.tempC > 0) temperature = "+$temperature"
        if (!degrees) {
            temperature = "${weather.current.tempF}°"
            if (weather.current.tempF > 0) temperature = "+$temperature"
        }
        val feelsTemperature =
            "Feels like ${if (degrees) weather.current.feelsLikeC else weather.current.feelsLikeF}°"
        val humidity = "${weather.current.humidity}%"
        val pressure = "${weather.current.pressureMmHg} mmHg"
        val wind = "${weather.current.windDir} ${weather.current.windMs} m/s"
        val gusts = "Gusts ${weather.current.gustMs} m/s"
        val sunrise = weather.forecast.forecastDay[0].astro.sunrise
        val sunset = weather.forecast.forecastDay[0].astro.sunset

        binding.city.text = weather.location.name
        binding.weatherStatus.text =
            WeatherConditions.getDescription(
                weather.current.condition.code,
                weather.location.localHour,
                sunrise,
                sunset
            )
        WeatherConditions.getBigIcon(
            weather.current.condition.code,
            weather.location.localHour,
            weather.current.tempC,
            sunrise,
            sunset,
        )?.let {
            binding.bigWeatherIcon.setImageResource(it)
        }
        binding.temperature.text = temperature
        binding.feelsTemperature.text = feelsTemperature
        binding.humidityValue.text = humidity
        binding.pressureValue.text = pressure
        binding.windValue.text = wind
        binding.gustsValue.text = gusts

        binding.city.visibility = View.VISIBLE
        binding.weatherStatusBlock.visibility = View.VISIBLE
        binding.temperatureBlock.visibility = View.VISIBLE
        binding.paramBlock.visibility = View.VISIBLE
    }

    private fun setTemperature(
        block: AppCompatTextView?,
        currentWeather: CurrentWeather,
        paramDay: Int,
        paramHour: Int
    ) {
        val temperature = if (getDegrees())
            currentWeather.forecast.forecastDay[paramDay].hour[paramHour].tempC
        else
            currentWeather.forecast.forecastDay[paramDay].hour[paramHour].tempF

        val tempForPrint = if (temperature > 0)
            "+$temperature°"
        else
            "$temperature°"
        block?.text = tempForPrint
    }

    private fun setSmallIconWeather(
        block: AppCompatImageView?,
        currentWeather: CurrentWeather,
        paramDay: Int,
        paramHour: Int,
    ) {
        val code = currentWeather.forecast.forecastDay[paramDay].hour[paramHour].condition.code
        val time = currentWeather.forecast.forecastDay[paramDay].hour[paramHour].time
        val temp = currentWeather.forecast.forecastDay[paramDay].hour[paramHour].tempC
        val sunrise = currentWeather.forecast.forecastDay[0].astro.sunrise
        val sunset = currentWeather.forecast.forecastDay[0].astro.sunset
        WeatherConditions.getSmallIcon(code, time, temp, sunrise, sunset)?.let {
            block?.setImageResource(it)
        }
    }

    private fun setTimesOfDay(paramHour: Int?): String {
        return when (paramHour) {
            8 -> getString(R.string.morning)
            13 -> getString(R.string.afternoon)
            18 -> getString(R.string.evening)
            else -> getString(R.string.night)
        }
    }

    private fun getDegrees() = prefs.getBoolean(KEY_DEGREES, true)

    private fun checkCurrentCity(position: Int) {
        val locationsList = mainViewModel?.locations
        val changedCity = locationsList?.getOrNull(position-1)
        if (city != changedCity)
            city = changedCity
    }

    companion object {
        fun newInstance(location: LocationEntity?, position: Int): ViewPagerFragment {
            val fragment = ViewPagerFragment()
            val bundle = Bundle()
            bundle.putParcelable(LOCATION_KEY, location)
            bundle.putInt(POSITION_KEY, position)
            fragment.arguments = bundle
            return fragment
        }

        private const val LOCATION_KEY = "LOCATION_KEY"
        private const val POSITION_KEY = "POSITION_KEY"
    }
}