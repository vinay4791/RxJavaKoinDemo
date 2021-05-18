package com.vinay.assignment.ui.movies

import com.vinay.assignment.ui.movies.backend.ApiMoviesListResponse
import com.vinay.assignment.ui.movies.backend.MoviesApiFetcher
import com.vinay.assignment.ui.movies.backend.MoviesBackend
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


private const val EMPTY_STRING = ""
@RunWith(MockitoJUnitRunner::class)
class MoviesApiFetcherTest {

    @Mock
    private lateinit var moviesBackend: MoviesBackend
    @Mock
    private lateinit var apiMoviesListResponse: ApiMoviesListResponse

    private lateinit var moviesApiFetcher: MoviesApiFetcher

    @Before
    fun setup() {
        whenever(moviesBackend.getNowPlayingMovies(getNowPlayingMoviesQueryMap())).thenReturn(Single.just(apiMoviesListResponse))
        whenever(moviesBackend.getPopularMovies(getNowPlayingMoviesQueryMap())).thenReturn(Single.just(apiMoviesListResponse))

        moviesApiFetcher = MoviesApiFetcher(moviesBackend)

    }

    @Test
    fun `should load now playing movies data from server`() {
        val testObserver = moviesApiFetcher.getNowPlayingMovies(getNowPlayingMoviesQueryMap()).test()
        testObserver.assertComplete()
        testObserver.assertValueCount(1)
    }

    @Test
    fun `should load popular movies data from server`() {
        val testObserver = moviesApiFetcher.getPopularMovies(getNowPlayingMoviesQueryMap()).test()
        testObserver.assertComplete()
        testObserver.assertValueCount(1)
    }

    private fun getNowPlayingMoviesQueryMap(): Map<String, String> {
        val queryMap = HashMap<String, String>()
        queryMap["api_key"] = EMPTY_STRING
        queryMap["language"] = EMPTY_STRING
        queryMap["page"] = EMPTY_STRING
        return queryMap
    }

}