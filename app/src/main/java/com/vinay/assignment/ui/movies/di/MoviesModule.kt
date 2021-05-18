package com.vinay.assignment.ui.movies.di

import com.vinay.assignment.ui.movies.MoviesListRepository
import com.vinay.assignment.ui.movies.MoviesListViewModel
import com.vinay.assignment.ui.movies.adapter.MoviesAdapter
import com.vinay.assignment.ui.movies.adapter.NowPlayingMoviesAdapter
import com.vinay.assignment.ui.movies.backend.MoviesApiFetcher
import com.vinay.assignment.ui.movies.backend.MoviesBackend
import com.vinay.assignment.ui.movies.viewstate.NowPlayingMoviesListConverter
import com.vinay.assignment.ui.movies.viewstate.PopularMoviesConverter
import com.vinay.assignment.ui.rx.AndroidSchedulingStrategyFactory
import com.vinay.assignment.ui.util.AppConstants
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val moviesModule = module {

    factory {
        MoviesAdapter()
    }

    factory {
        NowPlayingMoviesAdapter()
    }

    factory {
        moviesBackend(get())
    }

    factory {
        MoviesApiFetcher(get())
    }

    factory {
        PopularMoviesConverter(get())
    }

    factory {
        NowPlayingMoviesListConverter(get())
    }

    factory {
        MoviesListRepository(
            get(), get(),
            get(), AndroidSchedulingStrategyFactory.io()
        )
    }

    viewModel {
        MoviesListViewModel(get())
    }
}

fun moviesBackend(retrofit: Retrofit.Builder): MoviesBackend {
    return retrofit.baseUrl(AppConstants.BASE_URL).build().create(MoviesBackend::class.java)
}