package com.katyshevtseva.filmshelf.domain.model

data class Movie(
    val kpId: Int,
    val name: String?,
    val description: String?,
    val year: Int?,
    val posterUrl: String?,
    val ratingKp: Double?
)
