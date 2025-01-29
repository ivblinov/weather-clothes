package com.weatherclothes.artist.di.modules

import com.weatherclothes.artist.presentation.navigation.MainRouter
import com.weatherclothes.artist.presentation.navigation.Navigator
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NavigationModule {

    @Singleton
    @Provides
    fun provideNavigation(): Navigator = Navigator()

    @Singleton
    @Provides
    fun provideMainRouter(navigator: Navigator): MainRouter = navigator
}