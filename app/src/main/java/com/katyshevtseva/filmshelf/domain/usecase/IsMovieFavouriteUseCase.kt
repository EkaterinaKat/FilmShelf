package com.katyshevtseva.filmshelf.domain.usecase

import com.katyshevtseva.filmshelf.domain.repository.MovieRepository
import javax.inject.Inject

class IsMovieFavouriteUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {

    suspend operator fun invoke(kpId: Int): Boolean {
        return movieRepository.isMovieFavourite(kpId)
    }
}