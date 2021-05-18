package com.vinay.assignment.ui.details.backend

import io.reactivex.Single
import retrofit2.http.QueryMap

interface DetailsFetcher {

    fun getMovieDetails(@QueryMap map: Map<String, String>, movieId: String): Single<ApiDetailsResponse>

}