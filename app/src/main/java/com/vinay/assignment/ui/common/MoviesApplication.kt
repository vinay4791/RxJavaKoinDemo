package com.vinay.assignment.ui.common

import android.app.Application
import com.vinay.assignment.ui.base.moviesListAppModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MoviesApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MoviesApplication)
            modules(moviesListAppModules)
        }
    }
}