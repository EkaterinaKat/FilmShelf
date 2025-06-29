package com.katyshevtseva.filmshelf.domain.model

data class Genre(
    val name: String,
    val slug: String,
    val isEmpty: Boolean = false
) {

    override fun toString() = name

    companion object {
        val emptyGenre = Genre("-", "", true)
    }
}