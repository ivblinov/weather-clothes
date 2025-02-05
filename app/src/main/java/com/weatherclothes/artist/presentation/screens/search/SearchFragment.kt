package com.weatherclothes.artist.presentation.screens.search

import android.animation.ObjectAnimator
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.speech.RecognizerIntent
import android.text.Editable
import android.text.TextWatcher
import android.text.style.TextAppearanceSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.weatherclothes.artist.R
import com.weatherclothes.artist.databinding.FragmentSearchBinding
import com.weatherclothes.artist.domain.models.CurrentWeather
import com.weatherclothes.artist.domain.models.SearchLocation
import com.weatherclothes.artist.presentation.screens.main.KEY_DEGREES
import com.weatherclothes.artist.presentation.screens.search.recyclerView.SearchAdapter
import com.weatherclothes.artist.presentation.states.LocationState
import com.weatherclothes.artist.presentation.states.MainState
import com.weatherclothes.artist.utils.WeatherConditions
import com.weatherclothes.artist.utils.appComponent
import com.weatherclothes.artist.utils.lazyViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject
import kotlin.getValue

private const val TAG = "MyLog"

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var prefs: SharedPreferences

    val viewModel: SearchViewModel by lazyViewModel {
        requireContext().appComponent().searchViewModel().create()
    }

    private var textMainEditTextSpan: TextAppearanceSpan? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        inject()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.addCityScreen.visibility == View.GONE) {
                    viewModel.navigateUp(R.id.nav_places)
                } else if (shouldInterceptBackPress()) {
                    viewModel.setHidingState()
                } else {
                    viewModel.navigateUp(R.id.nav_places)
                }
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    private fun shouldInterceptBackPress(): Boolean {
        return binding.addCityScreen.translationY == 0f
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        textMainEditTextSpan = TextAppearanceSpan(requireContext(), R.style.TextMain_EditText)
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribe()

        binding.searchRV.adapter = SearchAdapter(
            styleBold = textMainEditTextSpan,
            onClickItem = viewModel::clickItem
        )

        binding.search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s?.length?.let {
                    hideHint(it)
                }
                s?.let {
                    viewModel.query = it.toString()
                    viewModel.getSearchLocation(it.toString())
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.microphone.setOnClickListener {
            startVoiceInput()
        }

        binding.locationCloseIV.setOnClickListener {
            viewModel.setHidingState()
        }

        binding.addButton.setOnClickListener {
            viewModel.addLocation()
            hideAddButton()
        }
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
                    viewModel.searchState.collect { state ->
                        when (state) {
                            MainState.Loading -> {}
                            MainState.Success -> {
                                getSearchAdapter().setList(
                                    viewModel.query,
                                    viewModel.places,
                                )
                            }
                            MainState.Error -> {}
                            MainState.Update -> {}
                        }
                    }
                }
                launch {
                    viewModel.placeState.collect { state ->
                        when (state) {
                            LocationState.Hiding -> {
                                dropAddCityScreen()
                                hideAddCityScreen()
                                showAddButton()
                            }

                            LocationState.Loading -> {
                                hideSoftInput()
                                showAddCityScreen()
                            }

                            LocationState.Success -> {
                                viewModel.weather?.let {
                                    inputDataInLocationScreen(
                                        viewModel.currentLocation,
                                        it
                                    )
                                    showAddLocationScreenItems()
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun startVoiceInput() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            putExtra(RecognizerIntent.EXTRA_PROMPT, "Name the place...")
        }

        try {
            startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT)
        } catch (_: ActivityNotFoundException) {
            Toast.makeText(
                requireContext(),
                "Voice input is not supported on this device",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_SPEECH_INPUT && resultCode == Activity.RESULT_OK) {
            val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            if (!result.isNullOrEmpty()) {
                binding.search.setText(result[0])
            }
        }
    }

    private fun hideAddButton() {
        binding.addedButton.visibility = View.VISIBLE
        binding.addButton.visibility = View.INVISIBLE
        binding.addButton.isClickable = false
    }

    private fun showAddButton() {
        binding.addedButton.visibility = View.INVISIBLE
        binding.addButton.visibility = View.VISIBLE
        binding.addButton.isClickable = true
    }

    private fun hideHint(length: Int) {
        if (length > 0)
            binding.hint.visibility = View.INVISIBLE
        else
            binding.hint.visibility = View.VISIBLE
    }

    private fun getSearchAdapter(): SearchAdapter = binding.searchRV.adapter as SearchAdapter

    private fun showAddCityScreen() {
        binding.searchBlock.visibility = View.GONE
        binding.searchRV.visibility = View.GONE
        binding.addCityScreen.visibility = View.VISIBLE
        binding.addCityScreen.translationY = 0f
        showProgressBar()
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideAddCityScreen() {
        binding.searchBlock.visibility = View.VISIBLE
        binding.searchRV.visibility = View.VISIBLE
        showSoftInput(binding.search)
    }

    private fun dropAddCityScreen() {
        val height = binding.addCityScreen.height.toFloat()
        val animator = ObjectAnimator.ofFloat(binding.addCityScreen, "translationY", 0f, height)
        animator.duration = 500
        animator.start()

        lifecycleScope.launch {
            delay(400)
            hideAddLocationScreenItems()
        }
    }

    private fun hideSoftInput() {
        val imm =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    private fun showSoftInput(editText: EditText) {
        editText.requestFocus()
        val imm =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun inputDataInLocationScreen(
        currentLocation: SearchLocation?,
        weather: CurrentWeather
    ) {

        val degrees = prefs.getBoolean(KEY_DEGREES, true)
        var temperature = "${weather.current.tempC}°"
        if (weather.current.tempC > 0) temperature = "+$temperature"
        if (!degrees) {
            temperature = "${weather.current.tempF}°"
            if (weather.current.tempF > 0) temperature = "+$temperature"
        }
        val feelsTemperature =
            "Feels like ${if (degrees) weather.current.feelsLikeC else weather.current.feelsLikeF}°"
        val sunrise = weather.forecast.forecastDay[0].astro.sunrise
        val sunset = weather.forecast.forecastDay[0].astro.sunset

        binding.nameCityTV.text = currentLocation?.name
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
        viewModel.manImage?.let {
            binding.man.setImageResource(it)
        }
        binding.temperature.text = temperature
        binding.feelsTemperature.text = feelsTemperature
    }

    private fun showAddLocationScreenItems() {
        binding.progressBar.visibility = View.INVISIBLE
        binding.locationCloseIV.visibility = View.VISIBLE
        binding.nameCityTV.visibility = View.VISIBLE
        binding.addButton.visibility = View.VISIBLE
        binding.temperatureBlock.visibility = View.VISIBLE
        binding.bigWeatherIcon.visibility = View.VISIBLE
        binding.man.visibility = View.VISIBLE
    }

    private fun hideAddLocationScreenItems() {
        binding.locationCloseIV.visibility = View.INVISIBLE
        binding.nameCityTV.visibility = View.INVISIBLE
        binding.addButton.visibility = View.INVISIBLE
        binding.temperatureBlock.visibility = View.INVISIBLE
        binding.bigWeatherIcon.visibility = View.INVISIBLE
        binding.man.visibility = View.INVISIBLE
    }

    companion object {
        private const val REQUEST_CODE_SPEECH_INPUT = 100
    }
}