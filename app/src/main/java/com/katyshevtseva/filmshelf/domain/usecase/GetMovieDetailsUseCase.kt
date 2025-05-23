package com.katyshevtseva.filmshelf.domain.usecase

import com.katyshevtseva.filmshelf.domain.model.Movie
import com.katyshevtseva.filmshelf.domain.repository.MovieRepository
import com.katyshevtseva.filmshelf.domain.result.Result
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {

    suspend operator fun invoke(movieId: Int): Result<Movie> {
        return movieRepository.getMovieDetails(movieId)
    }
}