package com.katyshevtseva.filmshelf.domain.model

data class Country(
    val name: String,
    val slug: String,
    val isEmpty: Boolean = false
) {

    override fun toString() = name

    companion object {
        val emptyCountry = Country("-", "", true)
    }
}