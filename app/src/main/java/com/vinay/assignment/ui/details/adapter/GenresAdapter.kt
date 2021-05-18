package com.vinay.assignment.ui.details.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vinay.assignment.R
import com.vinay.assignment.ui.details.viewstate.GenresViewState

class GenresAdapter(private val mItems: List<GenresViewState>) :
    RecyclerView.Adapter<GenresAdapter.DivItemViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GenresAdapter.DivItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.genre_item_layout, parent, false)
        return DivItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: DivItemViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    inner class DivItemViewHolder constructor(itemView: View) : RecyclerView.ViewHolder
        (itemView) {
        private val text: TextView = itemView.findViewById(R.id.genre_tv)

        fun onBind(position: Int) {
            text.text = mItems[position].name
        }
    }
}