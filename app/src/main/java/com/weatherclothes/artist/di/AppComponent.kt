package com.weatherclothes.artist.di

import android.app.Application
import com.weatherclothes.artist.di.modules.AppModule
import com.weatherclothes.artist.di.modules.LocationModule
import com.weatherclothes.artist.di.modules.NetworkModule
import com.weatherclothes.artist.di.modules.SharedPreferencesModule
import com.weatherclothes.artist.presentation.MainActivity
import com.weatherclothes.artist.presentation.SplashActivity
import com.weatherclothes.artist.presentation.screens.main.MainFragment
import com.weatherclothes.artist.presentation.screens.main.MainViewModel
import com.weatherclothes.artist.presentation.screens.main.ViewPagerFragment
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

    fun mainViewModel(): MainViewModel.Factory
}