package com.katyshevtseva.filmshelf.domain.usecase

import com.katyshevtseva.filmshelf.domain.model.MovieShortInfo
import com.katyshevtseva.filmshelf.domain.repository.MovieRepository
import javax.inject.Inject

class GetFavouriteMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {

    suspend operator fun invoke(): List<MovieShortInfo> {
        return movieRepository.getFavouriteMovies()
    }
}