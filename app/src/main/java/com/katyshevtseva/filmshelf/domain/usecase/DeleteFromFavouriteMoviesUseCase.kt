package com.katyshevtseva.filmshelf.domain.usecase

import com.katyshevtseva.filmshelf.domain.repository.MovieRepository
import javax.inject.Inject

class DeleteFromFavouriteMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {

    suspend operator fun invoke(kpId: Int) {
        return movieRepository.deleteFromFavouriteMovies(kpId)
    }
}