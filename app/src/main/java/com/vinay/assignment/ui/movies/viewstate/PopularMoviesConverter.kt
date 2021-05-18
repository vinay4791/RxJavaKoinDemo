package com.vinay.assignment.ui.movies.viewstate

import com.vinay.assignment.ui.movies.backend.ApiMoviesListResponse
import com.vinay.assignment.ui.movies.backend.Results
import com.vinay.assignment.ui.rx.Converter
import com.vinay.assignment.ui.util.AppConstants.EMPTY_DOUBLE
import com.vinay.assignment.ui.util.AppConstants.EMPTY_INT
import com.vinay.assignment.ui.util.Utils

class PopularMoviesConverter(private val utils: Utils) : Converter<ApiMoviesListResponse, MoviesListViewState> {
    override fun apply(apiMoviesListResponse: ApiMoviesListResponse): MoviesListViewState {

        val moviesComponentViewState = mutableListOf<MoviesComponentViewState>()

        moviesComponentViewState.add(MoviesListHeaderViewComponent("Most Popular"))

        apiMoviesListResponse.results.map { movieResult ->
            val movieInfo = getMoviesInfo(movieResult)
            moviesComponentViewState.add(movieInfo)
        }
        return MoviesListViewState.Success(moviesComponentViewState)
    }

    private fun getMoviesInfo(movieResult: Results): PopularMoviesInfo {
        return PopularMoviesInfo(
            id = movieResult.id.toString().orEmpty(),
            title = movieResult.original_title.orEmpty(),
            date = utils.getFormattedDate(movieResult.release_date.orEmpty()),
            thumbnailImageUrl = utils.getFormattedImageUrl(movieResult.backdrop_path.orEmpty()),
            imageUrl = utils.getFormattedImageUrl(movieResult.poster_path.orEmpty()),
            voteAverage = getPercentage(movieResult.vote_average ?: EMPTY_DOUBLE)
        )
    }

    private fun getPercentage(voteAverage: Double): Int {
        return if(voteAverage == EMPTY_DOUBLE){
            EMPTY_INT
        } else {
            ((voteAverage / 10) * 100).toInt()
        }
    }

}