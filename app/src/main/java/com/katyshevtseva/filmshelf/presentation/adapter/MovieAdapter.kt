package com.katyshevtseva.filmshelf.presentation.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.katyshevtseva.filmshelf.R
import com.katyshevtseva.filmshelf.databinding.MovieItemBinding
import com.katyshevtseva.filmshelf.domain.model.MovieShortInfo
import com.katyshevtseva.filmshelf.presentation.util.NULL_RATING_FOR_DISPLAY
import java.util.Locale

class MovieAdapter : ListAdapter<MovieShortInfo, MovieViewHolder>(MovieItemDiffCallback()) {
    var onMovieClickListener: ((MovieShortInfo) -> Unit)? = null
    var onReachEndListener: (() -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieViewHolder {
        return MovieViewHolder(
            MovieItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: MovieViewHolder,
        position: Int
    ) {
        val movie = getItem(position)

        if (movie.posterUrl != null) {
            Glide.with(holder.itemView).load(movie.posterUrl).into(holder.binding.posterImageView)
        } else {
            holder.binding.posterImageView.setImageResource(R.mipmap.default_poster)
        }

        holder.binding.ratingTextView.text =
            String.format(Locale.getDefault(), "%.1f", movie.ratingKp)
        holder.binding.ratingTextView.background =
            getRatingBackground(movie, holder.itemView.context)

        holder.itemView.setOnClickListener { view: View? ->
            onMovieClickListener?.invoke(movie)
        }

        if (position >= currentList.size - PRELOAD_OFFSET) {
            onReachEndListener?.invoke()
        }
    }

    private fun getRatingBackground(movie: MovieShortInfo, context: Context): Drawable? {
        val rating: Double = movie.ratingKp ?: NULL_RATING_FOR_DISPLAY
        val backgroundId: Int = when {
            rating > 7 -> R.drawable.circle_green
            rating in 5.0..7.0 -> R.drawable.circle_orange
            else -> R.drawable.circle_red
        }
        return ContextCompat.getDrawable(context, backgroundId)
    }

    companion object {
        private const val PRELOAD_OFFSET = 10
    }
}

class MovieViewHolder(val binding: MovieItemBinding) : RecyclerView.ViewHolder(binding.root)