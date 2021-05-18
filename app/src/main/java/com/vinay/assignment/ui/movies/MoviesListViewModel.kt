package com.vinay.assignment.ui.movies

import androidx.lifecycle.MutableLiveData
import com.vinay.assignment.ui.api.ErrorType
import com.vinay.assignment.ui.base.BaseViewModel
import com.vinay.assignment.ui.movies.viewstate.MoviesListViewState
import io.reactivex.disposables.CompositeDisposable

class MoviesListViewModel constructor(private val repository: MoviesListRepository) : BaseViewModel() {

    private val disposable = CompositeDisposable()
    private val nowPlayingMoviesListData = MutableLiveData<MoviesListViewState>()
    private val popularMoviesListData = MutableLiveData<MoviesListViewState>()

    fun nowPlayingMoviesListData(): MutableLiveData<MoviesListViewState> = nowPlayingMoviesListData
    fun popularMoviesListData(): MutableLiveData<MoviesListViewState> = popularMoviesListData

    fun fetchNowPlayingMoviesList(
        api_key: String,
        language: String,
        page: String
    ) {
        disposable.add(
            repository.fetchNowPlayingMoviesList(api_key, language, page.toString())
                .subscribe({ response ->
                    nowPlayingMoviesListData.postValue(response)
                },
                    { error ->
                        nowPlayingMoviesListData.postValue(MoviesListViewState.Error(ErrorType.UNKNOWN))
                    })
        )

    }

    fun fetchPopularMoviesList(
        api_key: String,
        language: String,
        page: String
    ){
        disposable.add(
            repository.fetchPopularMoviesList(api_key, language, page.toString())
                .subscribe({ response ->
                    popularMoviesListData.postValue(response)
                },
                    { error ->
                        popularMoviesListData.postValue(MoviesListViewState.Error(ErrorType.UNKNOWN))
                    })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}