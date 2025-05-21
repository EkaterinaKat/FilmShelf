package com.katyshevtseva.filmshelf.domain.model

data class MovieShortInfo(
    val kpId: Int,
    val name: String?,
    val posterUrl: String?,
    val ratingKp: Double?
)