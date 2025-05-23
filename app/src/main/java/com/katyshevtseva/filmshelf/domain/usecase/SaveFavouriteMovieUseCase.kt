package com.katyshevtseva.filmshelf.domain.usecase

import com.katyshevtseva.filmshelf.domain.model.Movie
import com.katyshevtseva.filmshelf.domain.repository.MovieRepository
import javax.inject.Inject

class SaveFavouriteMovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {

    suspend operator fun invoke(movie: Movie) {
        return movieRepository.saveFavouriteMovie(movie)
    }
}