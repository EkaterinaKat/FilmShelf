package com.katyshevtseva.filmshelf.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.katyshevtseva.filmshelf.databinding.TrailerItemBinding
import com.katyshevtseva.filmshelf.domain.model.Trailer

class TrailerAdapter : RecyclerView.Adapter<TrailerViewHolder>() {
    var trailers: List<Trailer> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onTrailerClickListener: ((Trailer) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrailerViewHolder {
        return TrailerViewHolder(
            TrailerItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TrailerViewHolder, position: Int) {
        val trailer = trailers[position]
        holder.binding.trailerNameTextView.text = trailer.name

        holder.itemView.setOnClickListener(View.OnClickListener { view: View? ->
            onTrailerClickListener?.invoke(trailer)
        })
    }

    override fun getItemCount(): Int {
        return trailers.size
    }
}

class TrailerViewHolder(val binding: TrailerItemBinding) : RecyclerView.ViewHolder(binding.root)