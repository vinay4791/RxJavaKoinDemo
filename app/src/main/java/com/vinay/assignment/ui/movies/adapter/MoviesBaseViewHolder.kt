package com.vinay.assignment.ui.movies.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vinay.assignment.ui.movies.viewstate.MoviesComponentViewState


abstract class MoviesBaseViewHolder (inflater: LayoutInflater,
                                     parent: ViewGroup,
                                     type: Int)
    : RecyclerView.ViewHolder(inflater.inflate(type, parent, false)){

    abstract fun bind(
        viewState: MoviesComponentViewState,
        position: Int
    )

    open fun unbind() {
        // no op
    }

}