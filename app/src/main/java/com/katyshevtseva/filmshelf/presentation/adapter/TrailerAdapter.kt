package com.katyshevtseva.filmshelf.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.katyshevtseva.filmshelf.R
import com.katyshevtseva.filmshelf.domain.model.Trailer

class TrailerAdapter : RecyclerView.Adapter<TrailerViewHolder>() {
    var trailers: List<Trailer> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onTrailerClickListener: ((Trailer) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrailerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.trailer_item, parent, false)
        return TrailerViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrailerViewHolder, position: Int) {
        val trailer = trailers[position]
        holder.trailerNameTextView.text = trailer.name

        holder.itemView.setOnClickListener(View.OnClickListener { view: View? ->
            onTrailerClickListener?.invoke(trailer)
        })
    }

    override fun getItemCount(): Int {
        return trailers.size
    }
}

class TrailerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val trailerNameTextView: TextView = itemView.findViewById<TextView?>(R.id.trailerNameTextView)
}