package com.weatherclothes.artist.presentation.screens.main

import android.Manifest
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
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
import androidx.lifecycle.ViewModelProvider
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
import com.weatherclothes.artist.presentation.screens.permissions.PermissionsFragment
import com.weatherclothes.artist.presentation.states.MainState
import com.weatherclothes.artist.utils.MainViewModelFactory
import com.weatherclothes.artist.utils.appComponent
import com.weatherclothes.artist.utils.isInternetAvailable
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "MyLog"
const val KEY_SEX = "KEY_SEX"
const val KEY_DEGREES = "KEY_DEGREES"

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

    private var viewModel: MainViewModel? = null

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory

    private val launcher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { map ->
        if (map.values.isNotEmpty() && map.values.all { it } && checkGPSEnabled()) {
            onPermissionsGranted()
        } else {
            onPermissionsDenied()
        }
    }

    private var sex = true
    private var degrees = true

    override fun onAttach(context: Context) {
        super.onAttach(context)
        inject()
        sex = getSex()
        degrees = getDegrees()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel =
            ViewModelProvider(requireActivity(), mainViewModelFactory)[MainViewModel::class.java]

        viewModel?.getAllLocations()

        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("InflateParams")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribe()

        val permissionRequested = prefsPermission.getBoolean(KEY_PERMISSION_REQUESTED, false)
        checkFirstLogin(permissionRequested)

        binding.settingsButton.setOnClickListener {
            showSettings()
            binding.touchTV.visibility = View.VISIBLE
            setDegreesView(getDegrees())
            setSexView(getSex())
        }

        binding.settingsClose.setOnClickListener {
            hideSettings()
            binding.touchTV.visibility = View.GONE
        }

        binding.touchTV.setOnClickListener {
            binding.touchTV.visibility = View.GONE
            hideSettings()
        }

        binding.degreesTitle.setOnClickListener {
            changeDegrees()
            viewModel?.changeMainState()
        }

        binding.degreesFahrenheitTitle.setOnClickListener {
            changeDegrees()
            viewModel?.changeMainState()
        }

        binding.maleTitle.setOnClickListener {
            changeSex()
            viewModel?.changeSexImage()
        }

        binding.femaleTitle.setOnClickListener {
            changeSex()
            viewModel?.changeSexImage()
        }

        binding.tryAgain.setOnClickListener {
            viewModel?.update()
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
                    viewModel?.mainState?.collect { state ->
                        when (state) {
                            MainState.Loading -> {
                                if (viewModel?.weather == null)
                                    showProgressBar()
                            }
                            MainState.Success -> {
                                hideProgressBar()
                            }
                            MainState.Error -> {
                                hideProgressBar()
                                binding.viewPager.visibility = View.GONE
                                binding.error.visibility = View.VISIBLE
                            }
                            MainState.Update -> {
                                requireActivity().run {
                                    finish()
                                    startActivity(intent)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun checkFirstLogin(permissionRequested: Boolean) {
        if (!permissionRequested) {
            with(prefsEditor) {
                putBoolean(KEY_PERMISSION_REQUESTED, true)
                apply()
            }
            checkAndRequestPermissions()
        } else {
            val checkPermissions = checkPermissions()
            if (checkPermissions) {
                onPermissionsGranted()
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

    private fun createViewPager(fragment: Fragment) {
        viewPager = binding.viewPager
        tabLayout = binding.tabLayout

        val fragments = mutableListOf<Fragment>(fragment)
        val titles = mutableListOf<String>(getString(R.string.your_location))
        viewModel?.locations?.forEach {
            val fragment = ViewPagerFragment.newInstance(it)
            fragments.add(fragment)
            titles.add(it.name)
        }
        adapter = WeatherLocationViewPagerAdapter(this.requireActivity(), fragments, titles)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            if (position == 0)
                tab.customView = LayoutInflater.from(requireContext()).inflate(R.layout.custom_tab_first, null)
            tab.text = adapter.getTitle(position)
        }.attach()
    }

    private fun onPermissionsGranted() {
        createViewPager(ViewPagerFragment())
        if (isInternetAvailable(requireContext())) {
            getLocation()
        } else {
            viewModel?.changeError()
        }
    }

    private fun onPermissionsDenied() {
        createViewPager(PermissionsFragment())
    }

    private fun requestLocation(
        fusedClient: FusedLocationProviderClient,
        cancellationSource: CancellationTokenSource
    ) {
        try {
            fusedClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    viewModel?.loadWeatherOfCurrentLocation(
                        latitude = location.latitude,
                        longitude = location.longitude
                    )
                }
            }
            val result = fusedClient.getCurrentLocation(
                Priority.PRIORITY_BALANCED_POWER_ACCURACY,
                cancellationSource.token
            )
            result.addOnSuccessListener {
                viewModel?.loadWeatherOfCurrentLocation(
                    latitude = it.latitude,
                    longitude = it.longitude
                )
            }
        } catch (e: SecurityException) {
            Log.d(TAG, "getLocation: exception = $e")
        }
    }

    private fun getSex() = prefsPermission.getBoolean(KEY_SEX, true)

    private fun getDegrees() = prefsPermission.getBoolean(KEY_DEGREES, true)

    private fun showSettings() {
        val height = binding.settings.height.toFloat()
        val animator = ObjectAnimator.ofFloat(binding.settings, "translationY", -height, 0f)
        animator.duration = 500
        animator.start()
    }

    private fun hideSettings() {
        val height = binding.settings.height.toFloat()
        val animator = ObjectAnimator.ofFloat(binding.settings, "translationY", 0f, -height)
        animator.duration = 500
        animator.start()
    }

    private fun setDegreesView(degrees: Boolean) {
        if (degrees) {
            binding.degreesTitle.setTextAppearance(R.style.TextSecAccent_ColorWhite)
            binding.checkMarkDegrees.visibility = View.VISIBLE
            binding.degreesFahrenheitTitle.setTextAppearance(R.style.TextSecondaryStyle_ColorWhite)
            binding.checkMarkDegreesFahrenheit.visibility = View.INVISIBLE
        } else {
            binding.degreesTitle.setTextAppearance(R.style.TextSecondaryStyle_ColorWhite)
            binding.checkMarkDegrees.visibility = View.INVISIBLE
            binding.degreesFahrenheitTitle.setTextAppearance(R.style.TextSecAccent_ColorWhite)
            binding.checkMarkDegreesFahrenheit.visibility = View.VISIBLE
        }
    }

    private fun setSexView(sex: Boolean) {
        if (sex) {
            with(binding) {
                maleTitle.setTextAppearance(R.style.TextSecAccent_ColorWhite)
                checkMarkMale.visibility = View.VISIBLE
                femaleTitle.setTextAppearance(R.style.TextSecondaryStyle_ColorWhite)
                checkMarkFemale.visibility = View.INVISIBLE
            }
        } else {
            with(binding) {
                maleTitle.setTextAppearance(R.style.TextSecondaryStyle_ColorWhite)
                checkMarkMale.visibility = View.INVISIBLE
                femaleTitle.setTextAppearance(R.style.TextSecAccent_ColorWhite)
                checkMarkFemale.visibility = View.VISIBLE
            }
        }
    }

    private fun changeDegrees() {
        if (getDegrees()) {
            prefsEditor.putBoolean(KEY_DEGREES, false)
            prefsEditor.apply()
        } else {
            prefsEditor.putBoolean(KEY_DEGREES, true)
            prefsEditor.apply()
        }
        setDegreesView(getDegrees())
    }

    private fun changeSex() {
        if (getSex()) {
            prefsEditor.putBoolean(KEY_SEX, false)
            prefsEditor.apply()
        } else {
            prefsEditor.putBoolean(KEY_SEX, true)
            prefsEditor.apply()
        }
        setSexView(getSex())
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
    }

    companion object {
        private const val KEY_PERMISSION_REQUESTED = "KEY_PERMISSION_REQUESTED"

        val REQUIRED_PERMISSIONS: Array<String> = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }
}