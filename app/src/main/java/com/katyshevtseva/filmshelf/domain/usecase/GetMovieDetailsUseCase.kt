package com.katyshevtseva.filmshelf.domain.usecase

import com.katyshevtseva.filmshelf.domain.model.Movie
import com.katyshevtseva.filmshelf.domain.repository.RemoteRepository
import com.katyshevtseva.filmshelf.domain.result.Result
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val remoteRepository: RemoteRepository
) {

    suspend operator fun invoke(movieId: Int): Result<Movie> {
        return remoteRepository.getMovieDetails(movieId)
    }
}