package com.katyshevtseva.filmshelf.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.katyshevtseva.filmshelf.domain.model.MovieShortInfo

class MovieItemDiffCallback : DiffUtil.ItemCallback<MovieShortInfo>() {

    override fun areItemsTheSame(
        oldItem: MovieShortInfo,
        newItem: MovieShortInfo
    ): Boolean {
        return oldItem.kpId == newItem.kpId
    }

    override fun areContentsTheSame(
        oldItem: MovieShortInfo,
        newItem: MovieShortInfo
    ): Boolean {
        return oldItem.kpId == newItem.kpId
    }
}