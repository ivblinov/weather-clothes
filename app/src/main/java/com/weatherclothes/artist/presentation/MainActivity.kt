package com.weatherclothes.artist.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.weatherclothes.artist.R
import com.weatherclothes.artist.databinding.ActivityMainBinding
import com.weatherclothes.artist.presentation.screens.main.MainViewModel
import com.weatherclothes.artist.utils.ViewModelFactory
import com.weatherclothes.artist.utils.appComponent
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var navController: NavController? = null

    @Inject lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        inject()

        val viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController =
            (supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment).navController

        navController?.let {
            binding.bottomNav.setupWithNavController(it)
        }
    }

    fun inject() {
        appComponent().inject(this)
    }
}