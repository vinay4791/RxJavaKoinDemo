package com.vinay.assignment.ui.movies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.vinay.assignment.rx.testObserver
import com.vinay.assignment.ui.movies.viewstate.MoviesListViewState
import com.google.common.truth.Truth
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MoviesListViewModelTest {

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: MoviesListRepository

    @Mock
    private lateinit var moviesListViewState: MoviesListViewState

    private lateinit var moviesListViewModel: MoviesListViewModel

    @Before
    fun setUp() {
        moviesListViewModel = MoviesListViewModel(repository)
    }

    @Test
    fun `should emit now playing view state when now playing movie list is fetched`() {
        whenever(repository.fetchNowPlayingMoviesList("", "", "")).thenReturn(
            Observable.just(
                moviesListViewState
            )
        )
        val responseObserver = moviesListViewModel.nowPlayingMoviesListData().testObserver()
        moviesListViewModel.fetchNowPlayingMoviesList("", "", "")
        Truth.assertThat(responseObserver.observedValues).isEqualTo(listOf(moviesListViewState))
    }

    @Test
    fun `should emit popular movies view state when popular movie list is fetched`() {
        whenever(repository.fetchPopularMoviesList("", "", "")).thenReturn(
            Observable.just(
                moviesListViewState
            )
        )
        val responseObserver = moviesListViewModel.popularMoviesListData().testObserver()
        moviesListViewModel.fetchPopularMoviesList("", "", "")
        Truth.assertThat(responseObserver.observedValues).isEqualTo(listOf(moviesListViewState))
    }

}