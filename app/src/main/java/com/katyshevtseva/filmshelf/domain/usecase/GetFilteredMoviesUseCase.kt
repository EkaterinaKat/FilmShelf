package com.katyshevtseva.filmshelf.domain.usecase

import com.katyshevtseva.filmshelf.domain.model.MovieShortInfo
import com.katyshevtseva.filmshelf.domain.model.SortType
import com.katyshevtseva.filmshelf.domain.repository.MovieRepository
import com.katyshevtseva.filmshelf.domain.result.Result
import javax.inject.Inject

class GetFilteredMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {

    suspend operator fun invoke(
        page: Int,
        sortType: SortType
    ): Result<List<MovieShortInfo>> {
        return movieRepository.getFilteredMovies(page, sortType)
    }
}