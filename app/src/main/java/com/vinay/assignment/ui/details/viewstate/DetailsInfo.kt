package com.vinay.assignment.ui.details.viewstate

import com.vinay.assignment.ui.util.AppConstants.EMPTY_INT
import com.vinay.assignment.ui.util.AppConstants.EMPTY_STRING

data class DetailsInfo(
    var id: Int,
    var overview: String,
    var releaseDate: String,
    var backdropPath: String,
    var posterPath: String,
    var title: String,
    var runtime: String,
    var genres: List<GenresViewState>
) {
    companion object {
        val EMPTY = DetailsInfo(
            id = EMPTY_INT,
            overview = EMPTY_STRING,
            title = EMPTY_STRING,
            releaseDate = EMPTY_STRING,
            backdropPath = EMPTY_STRING,
            posterPath = EMPTY_STRING,
            runtime = EMPTY_STRING,
            genres = emptyList()
        )
    }
}

data class GenresViewState(var id: Int,
                           var name: String) {
    companion object {
        var EMPTY = GenresViewState(
            id = EMPTY_INT,
            name = EMPTY_STRING
        )
    }
}
