package com.vinay.assignment.ui.movies.viewstate

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MoviesListHeaderViewComponent(val headerString : String) : MoviesComponentViewState,
    Parcelable {
    override fun type(): MoviesComponentViewState.Type = MoviesComponentViewState.Type.MOVIES_HEADER
}
