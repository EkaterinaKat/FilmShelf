package com.katyshevtseva.filmshelf.data.repository

import com.katyshevtseva.filmshelf.data.remote.ApiService
import com.katyshevtseva.filmshelf.data.remote.model.MovieResponse
import com.katyshevtseva.filmshelf.domain.repository.MoviesRepository
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : MoviesRepository {

    override suspend fun getBestMovies(): MovieResponse {
        return apiService.loadMovies(
            1,
            "2BW30XT-0E84FXT-PC1P59Z-1BW2MWB",
            "votes.kp",
            "-1",
            "3-10",
            "20"
        )
    }

}