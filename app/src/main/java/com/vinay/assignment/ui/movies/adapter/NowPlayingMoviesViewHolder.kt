package com.vinay.assignment.ui.movies.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.vinay.assignment.R
import com.vinay.assignment.ui.movies.viewstate.MoviesComponentViewState
import com.vinay.assignment.ui.movies.viewstate.NowPlayingMoviesComponent
import kotlinx.android.synthetic.main.now_playing_parent_layout.view.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class NowPlayingMoviesViewHolder(
    val inflater: LayoutInflater,
    val parent: ViewGroup,
    val listener: Listener
) : MoviesBaseViewHolder(inflater, parent, R.layout.now_playing_parent_layout), KoinComponent {

    val adapter: NowPlayingMoviesAdapter by inject()

    override fun bind(viewState: MoviesComponentViewState, position: Int) {

        val nowPlayingMovieListItem = (viewState as NowPlayingMoviesComponent)

        adapter.setListener(listener)
        adapter.setItems(nowPlayingMovieListItem.nowPlayingMoviesInfoList)
        itemView.now_playing_recycler_view.layoutManager = LinearLayoutManager(
                parent.context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        itemView.now_playing_recycler_view.adapter = adapter


    }

    interface Listener : NowPlayingMoviesItemViewHolder.Listener

}