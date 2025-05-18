package com.katyshevtseva.filmshelf.domain.usecase

import com.katyshevtseva.filmshelf.domain.model.Movie
import com.katyshevtseva.filmshelf.domain.repository.LocalRepository
import javax.inject.Inject

class IsMovieFavouriteUseCase @Inject constructor(
    private val localRepository: LocalRepository
) {

    suspend operator fun invoke(movie: Movie): Boolean {
        return localRepository.isMovieFavourite(movie)
    }
}