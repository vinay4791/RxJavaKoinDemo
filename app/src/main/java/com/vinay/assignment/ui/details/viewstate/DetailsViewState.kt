package com.vinay.assignment.ui.details.viewstate

import com.vinay.assignment.ui.api.ErrorType

sealed class DetailsViewState {

    object Loading : DetailsViewState()

    data class Success(val detailsInfo : DetailsInfo) : DetailsViewState()

    data class Error(val errorType : ErrorType) : DetailsViewState()

}