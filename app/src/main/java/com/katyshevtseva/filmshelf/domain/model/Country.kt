package com.katyshevtseva.filmshelf.domain.model

data class Country(
    val name: String,
    val slug: String
) {

    override fun toString() = name
}