package com.weatherclothes.artist.di

import android.app.Application
import com.weatherclothes.artist.presentation.screens.main.MainFragment
import com.weatherclothes.artist.presentation.screens.main.MainViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(mainFragment: MainFragment)

    fun mainViewModel(): MainViewModel.Factory
}