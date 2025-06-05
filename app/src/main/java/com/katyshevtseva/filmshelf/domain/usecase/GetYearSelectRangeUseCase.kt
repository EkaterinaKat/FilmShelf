package com.katyshevtseva.filmshelf.domain.usecase

import com.katyshevtseva.filmshelf.domain.model.YearRange
import com.katyshevtseva.filmshelf.domain.repository.MovieRepository
import com.katyshevtseva.filmshelf.domain.result.Result
import javax.inject.Inject

class GetYearSelectRangeUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {

    suspend operator fun invoke(): Result<YearRange> {
        return movieRepository.getYearSelectRange()
    }
}