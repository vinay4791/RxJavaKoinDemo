package com.vinay.assignment.ui.movies.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.vinay.assignment.R
import com.vinay.assignment.ui.movies.viewstate.MoviesComponentViewState
import com.vinay.assignment.ui.movies.viewstate.MoviesListHeaderViewComponent
import kotlinx.android.synthetic.main.list_header_component.view.*

class MoviesHeaderViewHolder(
    private val inflater: LayoutInflater,
    val parent: ViewGroup
) : MoviesBaseViewHolder(inflater, parent, R.layout.list_header_component) {
    override fun bind(viewState: MoviesComponentViewState, position: Int) {

        val nowPlayingMovieListItem = (viewState as MoviesListHeaderViewComponent)
        itemView.header_tv.text = nowPlayingMovieListItem.headerString
    }

}