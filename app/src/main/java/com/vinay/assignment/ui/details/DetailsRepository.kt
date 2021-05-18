package com.vinay.assignment.ui.details

import com.vinay.assignment.ui.api.ErrorType
import com.vinay.assignment.ui.details.backend.DetailsApiFetcher
import com.vinay.assignment.ui.details.viewstate.DetailsConverter
import com.vinay.assignment.ui.details.viewstate.DetailsViewState
import com.vinay.assignment.ui.rx.SchedulingStrategyFactory
import com.squareup.moshi.JsonEncodingException
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.functions.Function
import retrofit2.HttpException
import java.net.UnknownHostException

class DetailsRepository constructor(
    private val detailsApiFetcher: DetailsApiFetcher,
    private val detailsConverter: DetailsConverter,
    private val schedulingStrategyFactory: SchedulingStrategyFactory
) {

    fun fetchMovieDetails(
        api_key: String,
        language: String,
        movieId: String
    ): Observable<DetailsViewState> {

        val detailsFetcherObservable =
            detailsApiFetcher.getMovieDetails(getMovieDetailsQueryMap(api_key, language), movieId)
                .toObservable()

        return detailsFetcherObservable
            .map(detailsConverter)
            .startWith(DetailsViewState.Loading)
            .compose(ErrorConverter())
            .compose(schedulingStrategyFactory.create())

    }

    private fun getMovieDetailsQueryMap(
        api_key: String,
        language: String
    ): Map<String, String> {
        val queryMap = HashMap<String, String>()
        queryMap["api_key"] = api_key
        queryMap["language"] = language
        return queryMap
    }

    class ErrorConverter : ObservableTransformer<DetailsViewState, DetailsViewState> {
        override fun apply(upstream: Observable<DetailsViewState>): ObservableSource<DetailsViewState> {
            return upstream.onErrorResumeNext(Function<Throwable, ObservableSource<DetailsViewState>> { cause ->
                Observable.just(convertToCause(cause))
            })
        }

        private fun convertToCause(cause: Throwable): DetailsViewState {
            return when (cause) {
                is JsonEncodingException -> DetailsViewState.Error(ErrorType.UNKNOWN)
                is UnknownHostException -> DetailsViewState.Error(ErrorType.NO_INTERNET_CONNECTION)
                is HttpException -> {
                    DetailsViewState.Error(ErrorType.SERVER)
                }
                else -> DetailsViewState.Error(ErrorType.UNKNOWN)
            }
        }

    }

}