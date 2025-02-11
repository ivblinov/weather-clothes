package com.weatherclothes.artist.presentation.screens.places

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.weatherclothes.artist.R
import com.weatherclothes.artist.databinding.FragmentPlacesBinding
import com.weatherclothes.artist.presentation.screens.main.KEY_DEGREES
import com.weatherclothes.artist.presentation.screens.places.recyclerView.PlacesAdapter
import com.weatherclothes.artist.presentation.screens.places.recyclerView.swipeToDelete
import com.weatherclothes.artist.presentation.states.MainState
import com.weatherclothes.artist.utils.appComponent
import com.weatherclothes.artist.utils.isInternetAvailable
import com.weatherclothes.artist.utils.lazyViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "MyLog"
class PlacesFragment : Fragment() {

    private var _binding: FragmentPlacesBinding? = null
    private val binding get() = _binding!!

    val viewModel: PlacesViewModel by lazyViewModel {
        requireContext().appComponent().placesViewModel().create()
    }

    @Inject
    lateinit var prefsPermission: SharedPreferences

    override fun onAttach(context: Context) {
        super.onAttach(context)
        inject()
        loadWeather()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity?.finishAffinity()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlacesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribe()

        binding.placesRV.adapter = PlacesAdapter(
            degrees = getDegrees(),
            itemSelectedColor = getItemSelectedColor(),
            colorBgSecondary = getColorBgSecondary(),
            onItemMoved = viewModel::itemMove,
        )

        binding.addPlace.setOnClickListener {
            viewModel.openSearch()
        }

        binding.tryAgain.setOnClickListener {
            viewModel.setUpdateState()
        }

        swipeToDelete(binding.placesRV) { position ->

            Log.d(TAG, "position = $position")
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
                    viewModel.placeState.collect { state ->
                        when (state) {
                            MainState.Loading -> {

                            }
                            MainState.Success -> {
                                hideError()
                                if (viewModel.weatherLocations.isEmpty())
                                    showNotLocation()
                                else hideNotLocation()
                                getPlacesAdapter().setList(viewModel.weatherLocations)
                            }
                            MainState.Error -> {
                                showError()
                            }
                            MainState.Update -> {
                                loadWeather()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun showNotLocation() {
        binding.notLocationTV.visibility = View.VISIBLE
    }

    private fun hideNotLocation() {
        binding.notLocationTV.visibility = View.INVISIBLE
    }

    private fun showError() {
        binding.addPlace.visibility = View.GONE
        binding.notLocationTV.visibility = View.GONE
        binding.error.visibility = View.VISIBLE
    }

    private fun hideError() {
        binding.addPlace.visibility = View.VISIBLE
        binding.notLocationTV.visibility = View.VISIBLE
        binding.error.visibility = View.GONE
    }

    private fun loadWeather() {
        if (isInternetAvailable(requireContext()))
            viewModel.getLocations()
        else
            viewModel.setErrorState()
    }

    private fun getDegrees() = prefsPermission.getBoolean(KEY_DEGREES, true)

    private fun getPlacesAdapter(): PlacesAdapter = binding.placesRV.adapter as PlacesAdapter

    private fun getItemSelectedColor() = R.drawable.bg_item_selected_color

    private fun getColorBgSecondary() = R.drawable.bg_item_places
}