package com.vinay.assignment.ui.util

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val utilsModule = module {
    factory {
        Utils(androidContext())
    }
}