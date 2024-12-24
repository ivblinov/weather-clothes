package com.weatherclothes.artist.presentation

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.weatherclothes.artist.R
import com.weatherclothes.artist.databinding.ActivityMainBinding

private const val TAG = "MyLog"
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.VANILLA_ICE_CREAM) {
            Log.d(TAG, "version = 35")

            window.statusBarColor = this.getColor(R.color.statusBar)

            ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(0, systemBars.top, 0, 0)

//                v.updatePadding(top = insets.systemWindowInsetTop)

                insets
            }

        } else {
            Log.d(TAG, "version < 35")
        }

//        window.decorView.setOnApplyWindowInsetsListener(null)

//        window.insetsController?.systemBarsBehavior =
//            WindowInsetsController.BEHAVIOR_SHOW_BARS_BY_TOUCH

        navController =
            (supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment).navController

        navController?.let {
            binding.bottomNav.setupWithNavController(it)
        }
    }
}