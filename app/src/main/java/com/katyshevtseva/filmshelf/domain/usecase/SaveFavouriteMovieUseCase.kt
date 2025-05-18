package com.katyshevtseva.filmshelf.domain.usecase

import com.katyshevtseva.filmshelf.domain.model.Movie
import com.katyshevtseva.filmshelf.domain.repository.LocalRepository
import javax.inject.Inject

class SaveFavouriteMovieUseCase @Inject constructor(
    private val localRepository: LocalRepository
) {

    suspend operator fun invoke(movie: Movie) {
        return localRepository.saveFavouriteMovie(movie)
    }
}