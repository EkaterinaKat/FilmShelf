package com.katyshevtseva.filmshelf.domain.model

data class Movie(
    val id: Int = 0,
    val name: String? = null,
    val description: String? = null,
    val year: Int = 0,
    val posterUrl: String? = null,
    val ratingKp: Double = 0.0
)
