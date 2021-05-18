package com.vinay.assignment.ui.movies.backend

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface MoviesBackend {

    @GET("movie/now_playing")
    fun getNowPlayingMovies(
        @QueryMap map: Map<String, String>
    ): Single<ApiMoviesListResponse>

    @GET("movie/popular")
    fun getPopularMovies(
        @QueryMap map: Map<String, String>
    ): Single<ApiMoviesListResponse>

}
