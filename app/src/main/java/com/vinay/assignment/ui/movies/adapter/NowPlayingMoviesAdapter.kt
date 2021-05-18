package com.vinay.assignment.ui.movies.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vinay.assignment.R
import com.vinay.assignment.ui.movies.viewstate.NowPlayingMoviesInfo

class NowPlayingMoviesAdapter : RecyclerView.Adapter<NowPlayingMoviesItemViewHolder>() {

    private lateinit var listener : NowPlayingMoviesViewHolder.Listener
    private var nowPlayingMoviesItemList : List<NowPlayingMoviesInfo> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NowPlayingMoviesItemViewHolder {
        return NowPlayingMoviesItemViewHolder(
            LayoutInflater.from(parent.context).inflate
            (R.layout.now_playing_item_row, parent, false), listener)
    }

    override fun getItemCount(): Int = nowPlayingMoviesItemList.size

    override fun onBindViewHolder(holder: NowPlayingMoviesItemViewHolder, position: Int) {
        holder.bind(nowPlayingMoviesItemList[position])
    }

    fun setListener(listener: NowPlayingMoviesViewHolder.Listener) {
        this.listener = listener
    }

    fun setItems(nowPlayingMoviesItemList: List<NowPlayingMoviesInfo>) {
        this.nowPlayingMoviesItemList = nowPlayingMoviesItemList
        notifyDataSetChanged()
    }

}