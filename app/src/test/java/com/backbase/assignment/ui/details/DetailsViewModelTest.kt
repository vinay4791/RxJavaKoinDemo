package com.vinay.assignment.ui.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.vinay.assignment.rx.testObserver
import com.vinay.assignment.ui.details.viewstate.DetailsViewState
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
class DetailsViewModelTest {

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: DetailsRepository

    @Mock
    private lateinit var detailsViewState: DetailsViewState

    private lateinit var detailsViewModel: DetailsViewModel

    @Before
    fun setUp() {
        detailsViewModel = DetailsViewModel(repository)
    }

    @Test
    fun `should emit store pickup view state when now playing movie list is fetched`() {
        whenever(repository.fetchMovieDetails("", "", "")).thenReturn(
            Observable.just(
                detailsViewState
            )
        )
        val responseObserver = detailsViewModel.movieDetailData().testObserver()
        detailsViewModel.fetchMovieDetails("", "", "")
        Truth.assertThat(responseObserver.observedValues).isEqualTo(listOf(detailsViewState))
    }


}