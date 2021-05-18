package com.vinay.assignment.ui.movies.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.vinay.assignment.R
import com.vinay.assignment.ui.movies.viewstate.MoviesComponentViewState
import com.vinay.assignment.ui.movies.viewstate.PopularMoviesInfo
import com.vinay.assignment.ui.util.Utils
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.popular_movie_item.view.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PopularMoviesViewHolder(
    private val inflater: LayoutInflater,
    val parent: ViewGroup,
    private val listener: Listener
) : MoviesBaseViewHolder(inflater, parent, R.layout.popular_movie_item) , KoinComponent {
    override fun bind(viewState: MoviesComponentViewState, position: Int) {

        val utils: Utils by inject()

        val popularMovieListItem = (viewState as PopularMoviesInfo)

        itemView.title.text = popularMovieListItem.title
        itemView.releaseDate.text = popularMovieListItem.date

        Glide.with(itemView.context)
            .load(popularMovieListItem.imageUrl)
            .placeholder(utils.getProgressDrawable(parent.context))
            .into(itemView.poster)

        itemView.rating.init(popularMovieListItem.voteAverage)

        itemView.setOnClickListener {
            listener.popularMovieListItemSelected(popularMovieListItem.id)
        }
    }

    interface Listener {
        fun popularMovieListItemSelected(movieId: String)
    }

}