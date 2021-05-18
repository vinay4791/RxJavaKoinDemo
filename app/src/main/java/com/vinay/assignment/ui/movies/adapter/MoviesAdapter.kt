package com.vinay.assignment.ui.movies.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vinay.assignment.ui.movies.viewstate.MoviesComponentViewState

class MoviesAdapter : RecyclerView.Adapter<MoviesBaseViewHolder>() {

    private var moviesList: List<MoviesComponentViewState> = emptyList()
    private var listener: Listener = Listener.NO_OP

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesBaseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (MoviesComponentViewState.Type.from(viewType)) {
            MoviesComponentViewState.Type.MOVIES_LIST_POPULAR -> PopularMoviesViewHolder(
                inflater, parent,
                listener
            )
            MoviesComponentViewState.Type.MOVIES_LIST_NOW_PLAYING -> NowPlayingMoviesViewHolder(
                inflater, parent,
                listener
            )
            MoviesComponentViewState.Type.MOVIES_HEADER -> MoviesHeaderViewHolder(inflater, parent)
        }
    }

    fun setListener(listener: Listener) {
        this.listener = listener
    }

    interface Listener : NowPlayingMoviesViewHolder.Listener,
        PopularMoviesViewHolder.Listener {
        companion object {
            val NO_OP: Listener = object : Listener {
                override fun nowPlayingMovieListItemSelected(movieId: String) {
                    //NO_OP
                }

                override fun popularMovieListItemSelected(movieId: String) {
                    //NO_OP
                }

            }
        }
    }

    override fun onBindViewHolder(holder: MoviesBaseViewHolder, position: Int) {
        holder.bind(moviesList[position], position)
    }

    override fun getItemCount(): Int = moviesList.size

    override fun getItemViewType(position: Int): Int {
        return moviesList[position].type().value()
    }

    fun setItems(cartList: List<MoviesComponentViewState>) {
        this.moviesList = cartList
        notifyDataSetChanged()
    }

    fun getItems(): List<MoviesComponentViewState> {
        return moviesList
    }

}