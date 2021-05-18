package com.vinay.assignment.ui.details.backend

import io.reactivex.Single

class DetailsApiFetcher constructor(private val detailsBackend: DetailsBackend) : DetailsFetcher {

    override fun getMovieDetails(
        map: Map<String, String>,
        movieId: String
    ): Single<ApiDetailsResponse> {
        return detailsBackend.getMovieDetails(movieId, map)
    }

}