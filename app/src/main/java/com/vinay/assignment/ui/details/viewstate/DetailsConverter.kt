package com.vinay.assignment.ui.details.viewstate

import com.vinay.assignment.ui.details.backend.ApiDetailsResponse
import com.vinay.assignment.ui.details.backend.Genre
import com.vinay.assignment.ui.rx.Converter
import com.vinay.assignment.ui.util.AppConstants.EMPTY_INT
import com.vinay.assignment.ui.util.AppConstants.EMPTY_STRING
import com.vinay.assignment.ui.util.AppConstants.HOUR_SUFFIX
import com.vinay.assignment.ui.util.AppConstants.MINUTE_SUFFIX
import com.vinay.assignment.ui.util.Utils

class DetailsConverter(private val utils: Utils) : Converter<ApiDetailsResponse, DetailsViewState> {

    override fun apply(apiDetailsResponse: ApiDetailsResponse): DetailsViewState {
        val detailsInfo = DetailsInfo.EMPTY
        detailsInfo.id = apiDetailsResponse.id ?: EMPTY_INT
        detailsInfo.overview = apiDetailsResponse.overview.orEmpty()
        detailsInfo.releaseDate = utils.getFormattedDate(apiDetailsResponse.release_date.orEmpty())
        detailsInfo.backdropPath = utils.getFormattedImageUrl( apiDetailsResponse.backdrop_path.orEmpty())
        detailsInfo.posterPath = utils.getFormattedImageUrl(apiDetailsResponse.poster_path.orEmpty())
        detailsInfo.title = apiDetailsResponse.title.orEmpty()
        detailsInfo.runtime = getFormattedRunningTime(apiDetailsResponse.runtime)

        detailsInfo.genres = apiDetailsResponse.genres?.let{
            getGenres(apiDetailsResponse.genres)
        } ?: emptyList()

        return DetailsViewState.Success(detailsInfo)
    }

    private fun getFormattedRunningTime(runtime: Int?): String {
        return runtime?.let {
            var runtimeString = EMPTY_STRING
            val minutes = (runtime % 60).toString()
            val hours = (runtime / 60).toString()
            runtimeString = hours + HOUR_SUFFIX + minutes + MINUTE_SUFFIX
            runtimeString
        } ?: EMPTY_STRING
    }

    private fun getGenres(genreList :List<Genre>) : List<GenresViewState>{
        val genresViewStateList = mutableListOf<GenresViewState>()
        genreList.map {  genre ->
            val genresViewState = getGenreViewState(genre)
            genresViewStateList.add(genresViewState)
        }
        return genresViewStateList
    }

    private fun getGenreViewState(genre: Genre) : GenresViewState{
        return GenresViewState(
            id = genre.id ?: EMPTY_INT,
            name = genre.name ?: EMPTY_STRING
        )
    }

}