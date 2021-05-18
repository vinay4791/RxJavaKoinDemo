package com.vinay.assignment.ui.movies

import com.vinay.assignment.rx.TestSchedulingStrategyFactory
import com.vinay.assignment.ui.api.ErrorType
import com.vinay.assignment.ui.movies.backend.ApiMoviesListResponse
import com.vinay.assignment.ui.movies.backend.MoviesApiFetcher
import com.vinay.assignment.ui.movies.backend.MoviesBackend
import com.vinay.assignment.ui.movies.viewstate.MoviesListViewState
import com.vinay.assignment.ui.movies.viewstate.NowPlayingMoviesListConverter
import com.vinay.assignment.ui.movies.viewstate.PopularMoviesConverter
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
class MoviesListRepositoryTest {

    @Mock
    private lateinit var moviesBackend: MoviesBackend

    @Mock
    private lateinit var moviesApiFetcher: MoviesApiFetcher

    @Mock
    private lateinit var nowPlayingMoviesListConverter: NowPlayingMoviesListConverter

    @Mock
    private lateinit var popularMoviesListConverter: PopularMoviesConverter

    @Mock
    private lateinit var moviesListViewState: MoviesListViewState

    @Mock
    private lateinit var apiMoviesListResponse: ApiMoviesListResponse

    private lateinit var repository: MoviesListRepository

    @Before
    fun setUp() {
        repository = MoviesListRepository(
            moviesApiFetcher,
            nowPlayingMoviesListConverter,
            popularMoviesListConverter,
            TestSchedulingStrategyFactory.immediate())
    }

    @Test
    fun `should return correct movie view state when getNowPlayingMovies API is called`() {
        whenever(moviesApiFetcher.getNowPlayingMovies(getNowPlayingMoviesQueryMap())).thenReturn(Single.just(apiMoviesListResponse))
        whenever(nowPlayingMoviesListConverter.apply(apiMoviesListResponse)).thenReturn(moviesListViewState)
        val observer = repository
            .fetchNowPlayingMoviesList(EMPTY_STRING, EMPTY_STRING,EMPTY_STRING).test()
        observer.assertValues(MoviesListViewState.Loading, moviesListViewState)
    }

    @Test
    fun `should return error viewState while  fetching  movies`() {
        whenever(moviesApiFetcher.getNowPlayingMovies(getNowPlayingMoviesQueryMap())).thenReturn(Single.error(
            JsonEncodingException("json error")
        ))
        val testObserver = repository
            .fetchNowPlayingMoviesList(EMPTY_STRING, EMPTY_STRING, EMPTY_STRING)
            .test()
        val errorViewState = MoviesListViewState.Error(ErrorType.UNKNOWN)
        testObserver.assertValues(MoviesListViewState.Loading, errorViewState)
    }

    @Test
    fun `should return correct movie view state when getPopularMovies API is called`() {
        whenever(moviesApiFetcher.getPopularMovies(getNowPlayingMoviesQueryMap())).thenReturn(Single.just(apiMoviesListResponse))
        whenever(popularMoviesListConverter.apply(apiMoviesListResponse)).thenReturn(moviesListViewState)
        val observer = repository
            .fetchPopularMoviesList(EMPTY_STRING, EMPTY_STRING,EMPTY_STRING).test()
        observer.assertValues(MoviesListViewState.Loading, moviesListViewState)
    }

    @Test
    fun `should return error viewState while  fetching popular movies`() {
        whenever(moviesApiFetcher.getPopularMovies(getNowPlayingMoviesQueryMap())).thenReturn(Single.error(
            JsonEncodingException("json error")
        ))
        val testObserver = repository
            .fetchPopularMoviesList(EMPTY_STRING, EMPTY_STRING, EMPTY_STRING)
            .test()
        val errorViewState = MoviesListViewState.Error(ErrorType.UNKNOWN)
        testObserver.assertValues(MoviesListViewState.Loading, errorViewState)
    }

    private fun getNowPlayingMoviesQueryMap(): Map<String, String> {
        val queryMap = HashMap<String, String>()
        queryMap["api_key"] = EMPTY_STRING
        queryMap["language"] = EMPTY_STRING
        queryMap["page"] = EMPTY_STRING
        return queryMap
    }

}