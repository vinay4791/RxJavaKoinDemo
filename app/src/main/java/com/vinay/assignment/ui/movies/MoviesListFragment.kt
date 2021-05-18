package com.vinay.assignment.ui.movies

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.vinay.assignment.R
import com.vinay.assignment.ui.base.BaseFragment
import com.vinay.assignment.ui.movies.adapter.MoviesAdapter
import com.vinay.assignment.ui.movies.viewstate.MoviesComponentViewState
import com.vinay.assignment.ui.movies.viewstate.MoviesListViewState
import com.vinay.assignment.ui.util.AppConstants
import com.vinay.assignment.ui.util.AppConstants.MOVIE_ID
import com.vinay.assignment.ui.util.Utils
import kotlinx.android.synthetic.main.fragment_list.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoviesListFragment : BaseFragment() {

    private val viewModel: MoviesListViewModel by viewModel()
    private val adapter: MoviesAdapter by inject()
    private var adapterListData = mutableListOf<MoviesComponentViewState>()
    private lateinit var rootView: View
    private var isNavigatedToDetailScren = false
    private val utils: Utils by inject()

    override fun onCreateView (
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_list, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(!isNavigatedToDetailScren){
            fetchNowPlayingMoviesListData()
        }
        initialize()
    }

    private fun initialize() {
        setUpCartRecyclerView()
        observeNowPlayingMoviesListData()
        observePopularMoviesListData()
        fetchNowPlayingMoviesListData()
    }

    private fun setUpCartRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
        adapter.setListener(listener)
    }

    private val listener = object : MoviesAdapter.Listener {
        override fun nowPlayingMovieListItemSelected(movieId: String) {
            navigateToDetailFragment(movieId)
        }

        override fun popularMovieListItemSelected(movieId: String) {
            navigateToDetailFragment(movieId)
        }
    }

    private fun navigateToDetailFragment(movieId: String){
        val bundle = bundleOf(
            MOVIE_ID to movieId
        )
        isNavigatedToDetailScren = true
        Navigation.findNavController(rootView).navigate(R.id.action_listFragment_to_detailFragment, bundle)
    }

    private fun fetchNowPlayingMoviesListData() {
        if(utils.hasInternet()) {
            viewModel.fetchNowPlayingMoviesList(
                AppConstants.API_KEY,
                AppConstants.DEFAULT_LANGUAGE,
                AppConstants.INITIAL_PAGE.toString()
            )
        } else{
            showError()
        }
    }

    private fun fetchPopularMoviesList() {
        viewModel.fetchPopularMoviesList(
            AppConstants.API_KEY,
            AppConstants.DEFAULT_LANGUAGE,
            AppConstants.INITIAL_PAGE.toString()
        )
    }

    private fun observeNowPlayingMoviesListData() {
        viewModel.nowPlayingMoviesListData()
            .observe(viewLifecycleOwner, Observer { moviesListData ->
                when (moviesListData) {
                    is MoviesListViewState.Loading -> showApiLoadingIndicator()
                    is MoviesListViewState.Success -> {
                        showNowPlayingMoviesListData(moviesListData.moviesInfoList)
                        hideApiLoadingIndicator()
                    }
                    is MoviesListViewState.Error -> {
                        hideApiLoadingIndicator()
                        showError()
                    }
                }
            })
    }

    private fun observePopularMoviesListData() {
        viewModel.popularMoviesListData().observe(viewLifecycleOwner, Observer { moviesListData ->
            when (moviesListData) {
                is MoviesListViewState.Loading -> showApiLoadingIndicator()
                is MoviesListViewState.Success -> {
                    showPopularMoviesListData(moviesListData.moviesInfoList)
                    hideApiLoadingIndicator()
                }
                is MoviesListViewState.Error -> {
                    hideApiLoadingIndicator()
                    showError()
                }
            }
        })
    }

    private fun showNowPlayingMoviesListData(moviesComponentList: List<MoviesComponentViewState>) {
        adapterListData.clear()
        adapterListData.addAll(moviesComponentList)
        if(!isNavigatedToDetailScren){
            fetchPopularMoviesList()
        }
    }

    private fun showPopularMoviesListData(moviesListData: List<MoviesComponentViewState>) {
        adapterListData.addAll(moviesListData)
        adapter.setItems(adapterListData)
        isNavigatedToDetailScren = false
    }

    private fun showError() {
        Toast.makeText(requireContext(),resources.getString(R.string.error_string),Toast.LENGTH_SHORT).show()
    }

    private fun showApiLoadingIndicator() {
        loadingView.showLoading(R.color.loader_bg_white_transparent)
    }

    private fun hideApiLoadingIndicator() {
        loadingView.hideLoading()
    }

}