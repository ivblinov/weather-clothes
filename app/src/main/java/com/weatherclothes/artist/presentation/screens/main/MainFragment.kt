package com.weatherclothes.artist.presentation.screens.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.weatherclothes.artist.databinding.FragmentMainBinding
import com.weatherclothes.artist.presentation.states.MainState
import com.weatherclothes.artist.utils.appComponent
import com.weatherclothes.artist.utils.lazyViewModel
import kotlinx.coroutines.launch

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    val viewModel: MainViewModel by lazyViewModel {
        requireContext().appComponent().mainViewModel().create()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        inject()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribe()
        viewModel.loadWeather()
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
                    viewModel.mainState.collect { state ->
                        when (state) {
                            MainState.Loading -> {}
                            MainState.Success -> {}
                        }
                    }
                }
            }
        }
    }
}