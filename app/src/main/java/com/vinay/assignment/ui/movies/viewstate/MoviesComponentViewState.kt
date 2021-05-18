package com.vinay.assignment.ui.movies.viewstate

import androidx.annotation.LayoutRes
import com.vinay.assignment.R


interface MoviesComponentViewState {

    fun type(): Type

    enum class Type constructor(@LayoutRes private val layoutId: Int) {
        MOVIES_LIST_NOW_PLAYING(R.layout.now_playing_parent_layout),
        MOVIES_LIST_POPULAR(R.layout.popular_movie_item),
        MOVIES_HEADER(R.layout.list_header_component);

        companion object {
            fun from(value: Int): Type = Type.values()[value]
        }

        fun layoutId(): Int {
            return layoutId
        }

        fun value(): Int = ordinal
    }

}
