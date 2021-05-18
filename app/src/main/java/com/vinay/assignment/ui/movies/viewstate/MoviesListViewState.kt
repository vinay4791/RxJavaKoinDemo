package com.vinay.assignment.ui.movies.viewstate

import com.vinay.assignment.ui.api.ErrorType

sealed class MoviesListViewState {

    object Loading : MoviesListViewState()

    data class Success(val moviesInfoList : List<MoviesComponentViewState>) : MoviesListViewState()

    data class Error(val errorType : ErrorType) : MoviesListViewState()

}