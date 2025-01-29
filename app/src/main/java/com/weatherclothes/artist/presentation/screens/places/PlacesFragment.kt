package com.weatherclothes.artist.presentation.screens.places

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.weatherclothes.artist.databinding.FragmentPlacesBinding
import com.weatherclothes.artist.utils.appComponent
import com.weatherclothes.artist.utils.lazyViewModel

class PlacesFragment : Fragment() {

    private var _binding: FragmentPlacesBinding? = null
    private val binding get() = _binding!!

    val viewModel: PlacesViewModel by lazyViewModel {
        requireContext().appComponent().placesViewModel().create()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        inject()
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

        binding.addPlace.setOnClickListener {
            viewModel.openSearch()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun inject() {
        requireContext().appComponent().inject(this)
    }
}