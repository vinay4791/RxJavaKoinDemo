package com.vinay.assignment.ui.details.di

import com.vinay.assignment.ui.details.DetailsRepository
import com.vinay.assignment.ui.details.DetailsViewModel
import com.vinay.assignment.ui.details.backend.DetailsApiFetcher
import com.vinay.assignment.ui.details.backend.DetailsBackend
import com.vinay.assignment.ui.details.viewstate.DetailsConverter
import com.vinay.assignment.ui.rx.AndroidSchedulingStrategyFactory
import com.vinay.assignment.ui.util.AppConstants
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val detailsModule = module {

    factory {
        movieDetailsBackend(get())
    }

    factory {
        DetailsApiFetcher(get())
    }

    factory {
        DetailsConverter(get())
    }

    factory {
        DetailsRepository(
            get(), get(),
            AndroidSchedulingStrategyFactory.io()
        )
    }

    viewModel {
        DetailsViewModel(get())
    }
}

fun movieDetailsBackend(retrofit: Retrofit.Builder): DetailsBackend {
    return retrofit.baseUrl(AppConstants.BASE_URL).build().create(DetailsBackend::class.java)
}