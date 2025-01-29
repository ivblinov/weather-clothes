package com.weatherclothes.artist.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.weatherclothes.artist.R
import com.weatherclothes.artist.databinding.ActivityMainBinding
import com.weatherclothes.artist.presentation.navigation.Navigator
import com.weatherclothes.artist.presentation.screens.main.MainViewModel
import com.weatherclothes.artist.utils.MainViewModelFactory
import com.weatherclothes.artist.utils.appComponent
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject lateinit var navigator: Navigator
    private var navController: NavController? = null

    @Inject lateinit var mainViewModelFactory: MainViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        inject()

        val viewModel = ViewModelProvider(this, mainViewModelFactory)[MainViewModel::class.java]
        navController =
            (supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment).navController

        navController?.let {
            navigator.attachNavController(it, R.navigation.nav_graph)
            binding.bottomNav.setupWithNavController(it)
        }

        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_weather -> {
                    navigator.navigateToRoot(R.id.nav_weather)
                    true
                }
                R.id.nav_places -> {
                    navigator.navigateToRoot(R.id.nav_places)
                    true
                }
                else -> false
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        navController?.let {
            navigator.detachNavController(it)
        }
        navController = null
    }

    fun inject() {
        appComponent().inject(this)
    }
}