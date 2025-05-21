package com.katyshevtseva.filmshelf.domain.usecase

import com.katyshevtseva.filmshelf.domain.model.MovieShortInfo
import com.katyshevtseva.filmshelf.domain.repository.LocalRepository
import javax.inject.Inject

class GetFavouriteMoviesUseCase @Inject constructor(
    private val localRepository: LocalRepository
) {

    suspend operator fun invoke(): List<MovieShortInfo> {
        return localRepository.getFavouriteMovies()
    }
}