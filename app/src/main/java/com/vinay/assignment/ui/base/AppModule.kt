package com.vinay.assignment.ui.base

import com.vinay.assignment.ui.api.networkModule
import com.vinay.assignment.ui.details.di.detailsModule
import com.vinay.assignment.ui.movies.di.moviesModule
import com.vinay.assignment.ui.util.utilsModule

val moviesListAppModules = listOf(
    networkModule,
    utilsModule,
    moviesModule,
    detailsModule
)