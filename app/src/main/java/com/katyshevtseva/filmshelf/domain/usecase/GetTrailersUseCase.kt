package com.katyshevtseva.filmshelf.domain.usecase

import com.katyshevtseva.filmshelf.domain.model.Trailer
import com.katyshevtseva.filmshelf.domain.repository.MovieRepository
import com.katyshevtseva.filmshelf.domain.result.Result
import javax.inject.Inject

class GetTrailersUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {

    suspend operator fun invoke(movieId: Int): Result<List<Trailer>> {
        return movieRepository.getTrailers(movieId)
    }
}