package com.katyshevtseva.filmshelf.domain.repository

import com.katyshevtseva.filmshelf.domain.model.Movie
import com.katyshevtseva.filmshelf.domain.model.Trailer
import com.katyshevtseva.filmshelf.domain.result.Result

interface MoviesRepository {

    suspend fun getBestMovies(page: Int): Result<List<Movie>>

    suspend fun getMovieDetails(id: Int): Result<Movie>

    suspend fun getTrailers(movieId: Int): Result<List<Trailer>>
}