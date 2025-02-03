package com.weatherclothes.artist.di

import android.app.Application
import com.weatherclothes.artist.di.modules.AppModule
import com.weatherclothes.artist.di.modules.DataModule
import com.weatherclothes.artist.di.modules.LocationModule
import com.weatherclothes.artist.di.modules.NavigationModule
import com.weatherclothes.artist.di.modules.NetworkModule
import com.weatherclothes.artist.di.modules.SharedPreferencesModule
import com.weatherclothes.artist.presentation.MainActivity
import com.weatherclothes.artist.presentation.SplashActivity
import com.weatherclothes.artist.presentation.screens.main.MainFragment
import com.weatherclothes.artist.presentation.screens.main.MainViewModel
import com.weatherclothes.artist.presentation.screens.main.ViewPagerFragment
import com.weatherclothes.artist.presentation.screens.places.PlacesFragment
import com.weatherclothes.artist.presentation.screens.places.PlacesViewModel
import com.weatherclothes.artist.presentation.screens.search.SearchFragment
import com.weatherclothes.artist.presentation.screens.search.SearchViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        SharedPreferencesModule::class,
        AppModule::class,
        LocationModule::class,
        NetworkModule::class,
        NavigationModule::class,
        DataModule::class,
    ]
)
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(splashActivity: SplashActivity)

    fun inject(mainActivity: MainActivity)

    fun inject(mainFragment: MainFragment)

    fun inject(viewPagerFragment: ViewPagerFragment)

    fun inject(placesFragment: PlacesFragment)

    fun inject(searchFragment: SearchFragment)

    fun mainViewModel(): MainViewModel.Factory

    fun placesViewModel(): PlacesViewModel.Factory

    fun searchViewModel(): SearchViewModel.Factory
}