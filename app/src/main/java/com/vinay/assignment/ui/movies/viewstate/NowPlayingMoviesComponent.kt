package com.vinay.assignment.ui.movies.viewstate

import android.os.Parcelable
import com.vinay.assignment.ui.util.AppConstants
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NowPlayingMoviesComponent(
   val nowPlayingMoviesInfoList : List<NowPlayingMoviesInfo>
) : MoviesComponentViewState, Parcelable {
    override fun type(): MoviesComponentViewState.Type = MoviesComponentViewState.Type.MOVIES_LIST_NOW_PLAYING

}

@Parcelize
data class NowPlayingMoviesInfo(
    val id: String,
    val title: String,
    val imageUrl : String
) :  Parcelable {
    companion object {
        val EMPTY = NowPlayingMoviesInfo(
            id = AppConstants.EMPTY_STRING,
            title = AppConstants.EMPTY_STRING,
            imageUrl = AppConstants.EMPTY_STRING
        )
    }

}

