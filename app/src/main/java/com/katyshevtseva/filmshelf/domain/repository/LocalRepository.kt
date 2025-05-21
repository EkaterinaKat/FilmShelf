package com.katyshevtseva.filmshelf.domain.repository

import com.katyshevtseva.filmshelf.domain.model.Movie
import com.katyshevtseva.filmshelf.domain.model.MovieShortInfo

interface LocalRepository {

    suspend fun saveFavouriteMovie(movie: Movie)

    suspend fun deleteFromFavouriteMovies(kpId: Int)

    suspend fun getFavouriteMovies(): List<MovieShortInfo>

    suspend fun isMovieFavourite(kpId: Int): Boolean
}