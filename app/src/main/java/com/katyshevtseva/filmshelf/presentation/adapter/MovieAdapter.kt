package com.katyshevtseva.filmshelf.presentation.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.katyshevtseva.filmshelf.R
import com.katyshevtseva.filmshelf.domain.model.Movie
import java.util.Locale

class MovieAdapter : RecyclerView.Adapter<MovieViewHolder>() {
    var movies: List<Movie> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: MovieViewHolder,
        position: Int
    ) {
        val movie = movies[position]
        Glide.with(holder.itemView)
            .load(movie.posterUrl)
            .into(holder.posterImageView)
        holder.ratingTextView.text =
            String.format(Locale.getDefault(), "%.1f", movie.ratingKp ?: 0.0)
        holder.ratingTextView.background = getRatingBackground(movie, holder.itemView.context)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    private fun getRatingBackground(movie: Movie, context: Context): Drawable? {
        val rating: Double = movie.ratingKp
        val backgroundId: Int = when {
            rating > 7 -> R.drawable.circle_green
            rating in 5.0..7.0 -> R.drawable.circle_orange
            else -> R.drawable.circle_red
        }
        return ContextCompat.getDrawable(context, backgroundId)
    }
}

class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val posterImageView: ImageView =
        itemView.findViewById<ImageView>(R.id.posterImageView)
    val ratingTextView: TextView = itemView.findViewById<TextView?>(R.id.ratingTextView)
}