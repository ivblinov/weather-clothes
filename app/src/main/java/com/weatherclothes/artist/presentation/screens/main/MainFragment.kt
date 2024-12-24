package com.weatherclothes.artist.presentation.screens.main

import android.Manifest
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewpager2.widget.ViewPager2
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.weatherclothes.artist.R
import com.weatherclothes.artist.databinding.FragmentMainBinding
import com.weatherclothes.artist.presentation.screens.ViewPagerFragment
import com.weatherclothes.artist.presentation.screens.permissions.PermissionsFragment
import com.weatherclothes.artist.presentation.states.MainState
import com.weatherclothes.artist.utils.appComponent
import com.weatherclothes.artist.utils.lazyViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "MyLog"

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var fusedClient: FusedLocationProviderClient

    @Inject
    lateinit var cancellationSource: CancellationTokenSource

    @Inject
    lateinit var prefsPermission: SharedPreferences

    @Inject
    lateinit var prefsEditor: SharedPreferences.Editor

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var adapter: WeatherLocationViewPagerAdapter

    val viewModel: MainViewModel by lazyViewModel {
        requireContext().appComponent().mainViewModel().create()
    }

    private val launcher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { map ->
        if (map.values.isNotEmpty() && map.values.all { it } && checkGPSEnabled()) {
            onPermissionsGranted()
        } else {
            onPermissionsDenied()
        }
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

        subscribe()

        val permissionRequested = prefsPermission.getBoolean(KEY_PERMISSION_REQUESTED, false)
        checkFirstLogin(permissionRequested)

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

    private fun checkFirstLogin(permissionRequested: Boolean) {
        if (!permissionRequested) {
            Log.d(TAG, "Первый вход в приложение")
            with(prefsEditor) {
                putBoolean(KEY_PERMISSION_REQUESTED, true)
                apply()
            }
            checkAndRequestPermissions()
        } else {
            val checkPermissions = checkPermissions()
            Log.d(TAG, "Не первый вход в приложение - $checkPermissions")

            if (checkPermissions) {
                onPermissionsGranted()
                // запрашиваем координаты
            } else {
                onPermissionsDenied()
            }
        }
    }

    private fun checkAndRequestPermissions() {
        if (!checkPermissions()) {
            launcher.launch(REQUIRED_PERMISSIONS)
        } else {
            Log.d(TAG, "checkPer = true")
        }
    }

    private fun checkPermissions(): Boolean {
        return (REQUIRED_PERMISSIONS.all { permission ->
            ContextCompat.checkSelfPermission(
                requireContext(),
                permission
            ) == PackageManager.PERMISSION_GRANTED
        })
    }

    private fun checkGPSEnabled(): Boolean {
        val locationManager =
            requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        return enabled
    }

    private fun getLocation() {
        if (checkGPSEnabled()) {
            if (checkPermissions()) {
                requestLocation(fusedClient, cancellationSource)
            }
        } else {
            Log.d(TAG, "GPS выключен")
        }
    }

    private fun onPermissionsGranted() {
        getLocation()
        adapter.addFragment(ViewPagerFragment(), getString(R.string.your_location))
    }

    private fun onPermissionsDenied() {
        adapter.addFragment(PermissionsFragment(), getString(R.string.your_location))
    }

    private fun requestLocation(
        fusedClient: FusedLocationProviderClient,
        cancellationSource: CancellationTokenSource
    ) {
        try {
            val result = fusedClient.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                cancellationSource.token
            )
            result.addOnSuccessListener {
                Log.d(TAG, "getLocation: latitude = ${it.latitude}")
                Log.d(TAG, "getLocation: longitude = ${it.longitude}")

                viewModel.loadWeatherOfCurrentLocation(
                    latitude = it.latitude,
                    longitude = it.longitude
                )
            }
        } catch (e: SecurityException) {
            Log.d(TAG, "getLocation: exception = $e")
        }
    }

    companion object {
        private const val KEY_PERMISSION_REQUESTED = "KEY_PERMISSION_REQUESTED"

        val REQUIRED_PERMISSIONS: Array<String> = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }
}