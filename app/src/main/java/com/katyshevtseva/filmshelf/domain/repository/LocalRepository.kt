package com.katyshevtseva.filmshelf.domain.repository

import com.katyshevtseva.filmshelf.domain.model.Movie

interface LocalRepository {

    suspend fun saveFavouriteMovie(movie: Movie)

    suspend fun deleteFromFavouriteMovies(movie: Movie)

    suspend fun getFavouriteMovies(): List<Movie>
}