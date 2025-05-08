package com.katyshevtseva.filmshelf.domain.repository

import com.katyshevtseva.filmshelf.domain.model.Movie
import com.katyshevtseva.filmshelf.domain.result.Result

interface MoviesRepository {

    suspend fun getBestMovies(): Result<List<Movie>>

    suspend fun getMovieDetails(id: Int): Result<Movie>
}