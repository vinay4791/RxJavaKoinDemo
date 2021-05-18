package com.vinay.assignment.ui.movies.viewstate

import com.vinay.assignment.ui.movies.backend.ApiMoviesListResponse
import com.vinay.assignment.ui.movies.backend.Results
import com.vinay.assignment.ui.rx.Converter
import com.vinay.assignment.ui.util.Utils

class NowPlayingMoviesListConverter(private val utils: Utils) :
    Converter<ApiMoviesListResponse, MoviesListViewState> {
    override fun apply(apiMoviesListResponse: ApiMoviesListResponse): MoviesListViewState {
        val mowPlayingMoviesList = mutableListOf<NowPlayingMoviesInfo>()
        apiMoviesListResponse.results.map { movieResult ->
            val movieInfo = getMoviesInfo(movieResult)
            mowPlayingMoviesList.add(movieInfo)
        }
        val moviesComponentViewState = mutableListOf<MoviesComponentViewState>()
        moviesComponentViewState.add(MoviesListHeaderViewComponent("Now Playing"))
        moviesComponentViewState.addAll(listOf(NowPlayingMoviesComponent(mowPlayingMoviesList)))
        return MoviesListViewState.Success(moviesComponentViewState)
    }

    private fun getMoviesInfo(movieResult: Results): NowPlayingMoviesInfo {
        return NowPlayingMoviesInfo(
            id = movieResult.id.toString().orEmpty(),
            title = movieResult.original_title.orEmpty(),
            imageUrl = utils.getFormattedImageUrl(movieResult.poster_path.orEmpty())
        )
    }

}