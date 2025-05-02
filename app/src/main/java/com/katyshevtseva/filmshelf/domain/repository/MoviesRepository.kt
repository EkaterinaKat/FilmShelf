package com.katyshevtseva.filmshelf.domain.repository

import com.katyshevtseva.filmshelf.data.remote.model.MovieResponse

interface MoviesRepository {

    suspend fun getBestMovies(): MovieResponse
}