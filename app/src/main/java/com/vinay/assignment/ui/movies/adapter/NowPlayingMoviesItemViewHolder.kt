package com.vinay.assignment.ui.movies.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.vinay.assignment.ui.movies.viewstate.NowPlayingMoviesInfo
import com.vinay.assignment.ui.util.Utils
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.now_playing_item_row.view.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class NowPlayingMoviesItemViewHolder(
    val view: View,
    private val listener: Listener
) : RecyclerView.ViewHolder(view), KoinComponent {

    fun bind(nowPlayingMoviesInfo: NowPlayingMoviesInfo) {

        val utils: Utils by inject()

        Glide.with(itemView.context)
            .load(nowPlayingMoviesInfo.imageUrl)
            .placeholder(utils.getProgressDrawable(view.context))
            .into(view.now_playing_item_image_view)

        itemView.setOnClickListener {
            listener.nowPlayingMovieListItemSelected(nowPlayingMoviesInfo.id)
        }

    }

    interface Listener {
        fun nowPlayingMovieListItemSelected(movieId: String)
    }

}