package com.vinay.assignment.ui.movies.backend

import io.reactivex.Single
import retrofit2.http.QueryMap

interface MoviesFetcher {

    fun getNowPlayingMovies(@QueryMap map: Map<String, String>): Single<ApiMoviesListResponse>

    fun getPopularMovies(@QueryMap map: Map<String, String>): Single<ApiMoviesListResponse>

}