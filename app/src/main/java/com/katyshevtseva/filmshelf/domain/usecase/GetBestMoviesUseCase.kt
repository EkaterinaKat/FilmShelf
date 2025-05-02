package com.katyshevtseva.filmshelf.domain.usecase

import com.katyshevtseva.filmshelf.data.remote.model.MovieResponse
import com.katyshevtseva.filmshelf.domain.repository.MoviesRepository
import javax.inject.Inject

class GetBestMoviesUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {

    suspend operator fun invoke(): MovieResponse {
        return moviesRepository.getBestMovies()
    }
}