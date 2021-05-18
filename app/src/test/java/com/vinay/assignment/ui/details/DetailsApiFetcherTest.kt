package com.vinay.assignment.ui.details

import com.vinay.assignment.ui.details.backend.ApiDetailsResponse
import com.vinay.assignment.ui.details.backend.DetailsApiFetcher
import com.vinay.assignment.ui.details.backend.DetailsBackend
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

private const val EMPTY_STRING = ""

@RunWith(MockitoJUnitRunner::class)
class DetailsApiFetcherTest {

    @Mock
    private lateinit var detailsBackend: DetailsBackend

    @Mock
    private lateinit var apiDetailsResponse: ApiDetailsResponse

    private lateinit var detailsApiFetcher: DetailsApiFetcher

    @Before
    fun setup() {
        whenever(detailsBackend.getMovieDetails(EMPTY_STRING,getMovieDetailsQueryMap())).thenReturn(Single.just(apiDetailsResponse))
        detailsApiFetcher = DetailsApiFetcher(detailsBackend)
    }

    @Test
    fun `should load movie details data from server`() {
        val testObserver = detailsApiFetcher.getMovieDetails(getMovieDetailsQueryMap(), EMPTY_STRING).test()
        testObserver.assertComplete()
        testObserver.assertValueCount(1)
    }

    private fun getMovieDetailsQueryMap(): Map<String, String> {
        val queryMap = HashMap<String, String>()
        queryMap["api_key"] = EMPTY_STRING
        queryMap["language"] = EMPTY_STRING
        return queryMap
    }

}