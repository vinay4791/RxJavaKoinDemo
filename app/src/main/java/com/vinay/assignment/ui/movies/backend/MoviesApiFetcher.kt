package com.vinay.assignment.ui.movies.backend

import io.reactivex.Single

class MoviesApiFetcher constructor(private val moviesBackend: MoviesBackend) : MoviesFetcher {

    override fun getNowPlayingMovies(map: Map<String, String>): Single<ApiMoviesListResponse> {
       return moviesBackend.getNowPlayingMovies(map)
    }

    override fun getPopularMovies(map: Map<String, String>): Single<ApiMoviesListResponse> {
        return moviesBackend.getPopularMovies(map)
    }

}