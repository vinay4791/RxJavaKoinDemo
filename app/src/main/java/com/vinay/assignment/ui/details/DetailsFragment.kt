package com.vinay.assignment.ui.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.vinay.assignment.R
import com.vinay.assignment.ui.base.BaseFragment
import com.vinay.assignment.ui.details.adapter.GenresAdapter
import com.vinay.assignment.ui.details.viewstate.DetailsViewState
import com.vinay.assignment.ui.details.viewstate.GenresViewState
import com.vinay.assignment.ui.util.AppConstants
import com.vinay.assignment.ui.util.AppConstants.EMPTY_STRING
import com.vinay.assignment.ui.util.AppConstants.MOVIE_ID
import com.vinay.assignment.ui.util.Utils
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import kotlinx.android.synthetic.main.fragment_detail.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsFragment : BaseFragment() {

    private val viewModel: DetailsViewModel by viewModel()
    private val utils: Utils by inject()

    var movieId = EMPTY_STRING

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieId = arguments?.getString(MOVIE_ID) ?: EMPTY_STRING
        initialize()
    }

    private fun initialize() {
        observeMovieDetailsData()
        if(utils.hasInternet()){
            fetchNowPlayingMoviesListData()
        } else{
            showError()
        }
    }

    private fun fetchNowPlayingMoviesListData() {
        viewModel.fetchMovieDetails(
            AppConstants.API_KEY,
            AppConstants.DEFAULT_LANGUAGE,
            movieId
        )
    }

    private fun observeMovieDetailsData() {
        viewModel.movieDetailData().observe(viewLifecycleOwner, Observer { moviesData ->
            when (moviesData) {
                is DetailsViewState.Loading -> showApiLoadingIndicator()
                is DetailsViewState.Success -> {
                    showMovieDetailData(moviesData)
                    hideApiLoadingIndicator()
                }
                is DetailsViewState.Error -> {
                    hideApiLoadingIndicator()
                    showError()
                }
            }
        })
    }

    private fun showMovieDetailData(moviesData: DetailsViewState.Success) {
        val detailsInfo = moviesData.detailsInfo
        overview_content_tv.text = detailsInfo.overview
        release_date_tv.text = detailsInfo.releaseDate
        duration_tv.text = detailsInfo.runtime
        title_tv.text = detailsInfo.title

        back_button_iv.setOnClickListener {
            requireActivity().onBackPressed()
        }

        val requestOptions = RequestOptions().fitCenter()
        Glide.with(requireActivity())
            .load(detailsInfo.posterPath)
            .apply(requestOptions)
            .into(movie_iv)

        setGenres(detailsInfo.genres)
    }

    private fun setGenres(genres: List<GenresViewState>) {
        val layoutManager = FlexboxLayoutManager(requireActivity())
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.FLEX_START
        genres_recycler_view.layoutManager = layoutManager
        val adapter = GenresAdapter(genres)
        genres_recycler_view.adapter = adapter
    }

    private fun showError() {
        Toast.makeText(requireContext(),resources.getString(R.string.error_string), Toast.LENGTH_SHORT).show()
    }

    private fun showApiLoadingIndicator() {
        loadingView.showLoading(R.color.loader_bg_white_transparent)
    }

    private fun hideApiLoadingIndicator() {
        loadingView.hideLoading()
    }

}