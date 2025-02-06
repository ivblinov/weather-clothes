package com.weatherclothes.artist.presentation.screens.permissions

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.weatherclothes.artist.databinding.FragmentPermissionsBinding
import com.weatherclothes.artist.presentation.screens.main.MainFragment.Companion.REQUIRED_PERMISSIONS
import com.weatherclothes.artist.presentation.screens.main.MainViewModel
import com.weatherclothes.artist.utils.MainViewModelFactory
import com.weatherclothes.artist.utils.appComponent
import javax.inject.Inject

class PermissionsFragment : Fragment() {

    private var _binding: FragmentPermissionsBinding? = null
    private val binding get() = _binding!!

    private var mainViewModel: MainViewModel? = null

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory

    override fun onAttach(context: Context) {
        super.onAttach(context)
        inject()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainViewModel =
            ViewModelProvider(requireActivity(), mainViewModelFactory)[MainViewModel::class.java]
        _binding = FragmentPermissionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.grantPermission.setOnClickListener {
            openAppSettings()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            val checkPermissions = checkPermissions()
            if (checkPermissions) {
                mainViewModel?.update()
            }
        }
    }

    fun inject() {
        requireContext().appComponent().inject(this)
    }

    private fun checkPermissions(): Boolean {
        return (REQUIRED_PERMISSIONS.all { permission ->
            ContextCompat.checkSelfPermission(
                requireContext(),
                permission
            ) == PackageManager.PERMISSION_GRANTED
        })
    }

    private fun openAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", "com.weatherclothes.artist", null)
        }
        startActivityForResult(intent, LOCATION_PERMISSION_REQUEST_CODE)
    }

    companion object {

        private const val LOCATION_PERMISSION_REQUEST_CODE = 100
    }
}