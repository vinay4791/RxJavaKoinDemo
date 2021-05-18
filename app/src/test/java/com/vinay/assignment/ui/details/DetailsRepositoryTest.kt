package com.vinay.assignment.ui.details

import com.vinay.assignment.rx.TestSchedulingStrategyFactory
import com.vinay.assignment.ui.api.ErrorType
import com.vinay.assignment.ui.details.backend.ApiDetailsResponse
import com.vinay.assignment.ui.details.backend.DetailsApiFetcher
import com.vinay.assignment.ui.details.viewstate.DetailsConverter
import com.vinay.assignment.ui.details.viewstate.DetailsViewState
import com.nhaarman.mockitokotlin2.whenever
import com.squareup.moshi.JsonEncodingException
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

private const val EMPTY_STRING = ""

@RunWith(MockitoJUnitRunner::class)
class DetailsRepositoryTest {

    @Mock
    private lateinit var detailsApiFetcher: DetailsApiFetcher

    @Mock
    private lateinit var detailsConverter: DetailsConverter

    @Mock
    private lateinit var detailsViewState: DetailsViewState

    @Mock
    private lateinit var apiDetailsResponse: ApiDetailsResponse

    private lateinit var repository: DetailsRepository

    @Before
    fun setUp() {
        repository = DetailsRepository(
            detailsApiFetcher,
            detailsConverter,
            TestSchedulingStrategyFactory.immediate())
    }

    @Test
    fun `should return correct movie view state when  API is called`() {
        whenever(detailsApiFetcher.getMovieDetails(getMovieDetailsQueryMap(),"")).thenReturn(
            Single.just(apiDetailsResponse))
        whenever(detailsConverter.apply(apiDetailsResponse)).thenReturn(detailsViewState)
        val observer = repository
            .fetchMovieDetails(
                EMPTY_STRING,
                EMPTY_STRING,EMPTY_STRING).test()
        observer.assertValues(DetailsViewState.Loading, detailsViewState)
    }

    @Test
    fun `should return error viewState while  fetching  movies`() {
        whenever(detailsApiFetcher.getMovieDetails(getMovieDetailsQueryMap(),"")).thenReturn(Single.error(
            JsonEncodingException("json error")
        ))
        val testObserver = repository
            .fetchMovieDetails(
                EMPTY_STRING,
                EMPTY_STRING,EMPTY_STRING).test()
        val errorViewState = DetailsViewState.Error(ErrorType.UNKNOWN)
        testObserver.assertValues(DetailsViewState.Loading, errorViewState)
    }

    private fun getMovieDetailsQueryMap(): Map<String, String> {
        val queryMap = HashMap<String, String>()
        queryMap["api_key"] = EMPTY_STRING
        queryMap["language"] = EMPTY_STRING
        return queryMap
    }


}