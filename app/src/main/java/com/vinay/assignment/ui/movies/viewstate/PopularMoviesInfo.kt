package com.vinay.assignment.ui.movies.viewstate

import android.os.Parcelable
import com.vinay.assignment.ui.util.AppConstants.EMPTY_INT
import com.vinay.assignment.ui.util.AppConstants.EMPTY_STRING
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PopularMoviesInfo(
    val id: String,
    val title: String,
    val date: String,
    val thumbnailImageUrl : String,
    val imageUrl : String,
    val voteAverage: Int

) : MoviesComponentViewState, Parcelable {

    override fun type() = MoviesComponentViewState.Type.MOVIES_LIST_POPULAR

    companion object {
        val EMPTY = PopularMoviesInfo (
            id = EMPTY_STRING,
            title = EMPTY_STRING,
            date = EMPTY_STRING,
            thumbnailImageUrl = EMPTY_STRING,
            imageUrl = EMPTY_STRING,
            voteAverage = EMPTY_INT
        )
    }

}