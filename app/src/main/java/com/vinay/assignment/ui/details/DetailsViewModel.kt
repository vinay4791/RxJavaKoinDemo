package com.vinay.assignment.ui.details

import androidx.lifecycle.MutableLiveData
import com.vinay.assignment.ui.api.ErrorType
import com.vinay.assignment.ui.base.BaseViewModel
import com.vinay.assignment.ui.details.viewstate.DetailsViewState
import io.reactivex.disposables.CompositeDisposable

class DetailsViewModel constructor(private val repository: DetailsRepository) : BaseViewModel() {

    private val disposable = CompositeDisposable()
    private val movieDetailData = MutableLiveData<DetailsViewState>()

    fun movieDetailData(): MutableLiveData<DetailsViewState> = movieDetailData

    fun fetchMovieDetails(
        api_key: String,
        language: String,
        movieId: String
    ) {
        disposable.add(
            repository.fetchMovieDetails(api_key,language,movieId)
                .subscribe({ response ->
                    movieDetailData.postValue(response)
                }, { error ->
                        movieDetailData.postValue(DetailsViewState.Error(ErrorType.UNKNOWN))
                    })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}