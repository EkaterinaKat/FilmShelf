package com.katyshevtseva.filmshelf.domain.model

data class Genre(
    val name: String,
    val slug: String
) {

    override fun toString() = name
}