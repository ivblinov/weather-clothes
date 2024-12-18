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
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.weatherclothes.artist.databinding.FragmentMainBinding
import com.weatherclothes.artist.presentation.screens.ViewPagerFragment
import com.weatherclothes.artist.presentation.states.MainState
import com.weatherclothes.artist.utils.appComponent
import com.weatherclothes.artist.utils.lazyViewModel
import kotlinx.coroutines.launch

private const val TAG = "MyLog"
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var adapter: WeatherLocationViewPagerAdapter

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

        viewPager = binding.viewPager
        tabLayout = binding.tabLayout
        adapter = WeatherLocationViewPagerAdapter(this.requireActivity())

        adapter.addFragment(ViewPagerFragment(), "Your location")
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = adapter.getTitle(position)
//            if (position == 0) {
//                Log.d(TAG, "onViewCreated: ")
//                tab.setIcon(R.drawable.ic_location)
//            } else {
//                Log.d(TAG, "onViewCreated: No")
//            }
//            tabLayout.selectTab(tab, true)
        }.attach()

        // добавление новых табов
/*        val newTabIndex = adapter.itemCount + 1
        adapter.addFragment(ViewPagerFragment(), "Tab $newTabIndex")
        viewPager.currentItem = adapter.itemCount - 1*/

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