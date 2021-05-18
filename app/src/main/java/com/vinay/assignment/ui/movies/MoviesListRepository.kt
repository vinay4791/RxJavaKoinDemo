package com.vinay.assignment.ui.movies

import com.vinay.assignment.ui.api.ErrorType
import com.vinay.assignment.ui.movies.backend.MoviesApiFetcher
import com.vinay.assignment.ui.movies.viewstate.MoviesListViewState
import com.vinay.assignment.ui.movies.viewstate.NowPlayingMoviesListConverter
import com.vinay.assignment.ui.movies.viewstate.PopularMoviesConverter
import com.vinay.assignment.ui.rx.SchedulingStrategyFactory
import com.squareup.moshi.JsonEncodingException
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.functions.Function
import retrofit2.HttpException
import java.net.UnknownHostException

class MoviesListRepository constructor(
    private val moviesApiFetcher: MoviesApiFetcher,
    private val nowPlayingMoviesListConverter: NowPlayingMoviesListConverter,
    private val popularMoviesConverter: PopularMoviesConverter,
    private val schedulingStrategyFactory: SchedulingStrategyFactory
) {

    fun fetchNowPlayingMoviesList(api_key: String,
                                  language: String,
                                  page: String): Observable<MoviesListViewState> {

            val moviesApiFetcherObservable = moviesApiFetcher.
            getNowPlayingMovies(getMovieListFetchQueryMap(api_key, language, page)).toObservable()
            return moviesApiFetcherObservable
                .map(nowPlayingMoviesListConverter)
                .startWith(MoviesListViewState.Loading)
                .compose(ErrorConverter())
                .compose(schedulingStrategyFactory.create())
    }

    fun fetchPopularMoviesList(api_key: String,
                                  language: String,
                                  page: String): Observable<MoviesListViewState> {

        val moviesApiFetcherObservable = moviesApiFetcher.
        getPopularMovies(getMovieListFetchQueryMap(api_key, language, page)).toObservable()

        return moviesApiFetcherObservable
            .map(popularMoviesConverter)
            .startWith(MoviesListViewState.Loading)
            .compose(ErrorConverter())
            .compose(schedulingStrategyFactory.create())

    }

    private fun getMovieListFetchQueryMap(
        api_key: String,
        language: String,
        page: String
    ): Map<String, String> {
        val queryMap = HashMap<String, String>()
        queryMap["api_key"] = api_key
        queryMap["language"] = language
        queryMap["page"] = page
        return queryMap
    }

    class ErrorConverter : ObservableTransformer<MoviesListViewState, MoviesListViewState> {
        override fun apply(upstream: Observable<MoviesListViewState>): ObservableSource<MoviesListViewState> {
            return upstream.onErrorResumeNext(Function<Throwable, ObservableSource<MoviesListViewState>> { cause ->
                Observable.just(convertToCause(cause))
            })
        }

        private fun convertToCause(cause: Throwable): MoviesListViewState {
            return when (cause) {
                is JsonEncodingException -> MoviesListViewState.Error(ErrorType.UNKNOWN)
                is UnknownHostException -> MoviesListViewState.Error(ErrorType.NO_INTERNET_CONNECTION)
                is HttpException -> {
                    MoviesListViewState.Error(ErrorType.SERVER)
                }
                else -> MoviesListViewState.Error(ErrorType.UNKNOWN)
            }
        }

    }

}