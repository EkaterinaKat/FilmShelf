package com.katyshevtseva.filmshelf.domain.usecase

import com.katyshevtseva.filmshelf.domain.model.Trailer
import com.katyshevtseva.filmshelf.domain.repository.RemoteRepository
import com.katyshevtseva.filmshelf.domain.result.Result
import javax.inject.Inject

class GetTrailersUseCase @Inject constructor(
    private val remoteRepository: RemoteRepository
) {

    suspend operator fun invoke(movieId: Int): Result<List<Trailer>> {
        return remoteRepository.getTrailers(movieId)
    }
}