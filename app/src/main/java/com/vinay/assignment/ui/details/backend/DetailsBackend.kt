package com.vinay.assignment.ui.details.backend

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface DetailsBackend {

    @GET("movie/{id}")
    fun getMovieDetails(
        @Path("id") id: String,
        @QueryMap map: Map<String, String>
    ): Single<ApiDetailsResponse>

}