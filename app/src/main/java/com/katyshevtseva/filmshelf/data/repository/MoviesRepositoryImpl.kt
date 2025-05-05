package com.katyshevtseva.filmshelf.data.repository

import com.katyshevtseva.filmshelf.data.mapper.MovieMapper
import com.katyshevtseva.filmshelf.data.remote.ApiService
import com.katyshevtseva.filmshelf.domain.model.Movie
import com.katyshevtseva.filmshelf.domain.repository.MoviesRepository
import com.katyshevtseva.filmshelf.domain.result.Error
import com.katyshevtseva.filmshelf.domain.result.Result
import com.katyshevtseva.filmshelf.domain.result.Success
import java.io.IOException
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val mapper: MovieMapper
) : MoviesRepository {

    override suspend fun getBestMovies(): Result<List<Movie>> {
        return try {
            val response = apiService.loadMovies(
                1,
                "2BW30XT-0E84FXT-PC1P59Z-1BW2MWB",
                "votes.kp",
                "-1",
                "3-10",
                "20"
            )
            if (response.isSuccessful) {
                val movies =
                    response.body()?.movies?.map { mapper.mapDtoToDomainModel(it) } ?: listOf()
                Success(movies)
            } else {
                Error(RuntimeException("Ошибка сервера: ${response.code()}"))
            }
        } catch (e: IOException) {
            Error(RuntimeException("Ошибка сети: ${e.localizedMessage}", e))
        } catch (e: Exception) {
            Error(RuntimeException("Неизвестная ошибка: ${e.localizedMessage}", e))
        }
    }
}