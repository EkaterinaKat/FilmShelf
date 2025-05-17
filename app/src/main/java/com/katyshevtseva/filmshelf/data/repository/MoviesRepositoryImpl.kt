package com.katyshevtseva.filmshelf.data.repository

import com.katyshevtseva.filmshelf.data.mapper.MovieMapper
import com.katyshevtseva.filmshelf.data.remote.ApiService
import com.katyshevtseva.filmshelf.data.remote.model.MovieDto
import com.katyshevtseva.filmshelf.data.remote.model.MovieListDto
import com.katyshevtseva.filmshelf.data.remote.model.TrailerListDto
import com.katyshevtseva.filmshelf.domain.model.Movie
import com.katyshevtseva.filmshelf.domain.model.Trailer
import com.katyshevtseva.filmshelf.domain.repository.MoviesRepository
import com.katyshevtseva.filmshelf.domain.result.Error
import com.katyshevtseva.filmshelf.domain.result.Result
import com.katyshevtseva.filmshelf.domain.result.Success
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val mapper: MovieMapper
) : MoviesRepository {

    override suspend fun getBestMovies(page: Int): Result<List<Movie>> {
        return load({
            apiService.loadMovies(
                page,
                "2BW30XT-0E84FXT-PC1P59Z-1BW2MWB",
                "votes.kp",
                "-1",
                "3-10",
                "20"
            )
        }, { dto: MovieListDto? ->
            dto?.movies?.map { mapper.mapDtoToDomainModel(it) } ?: listOf()
        })
    }

    override suspend fun getMovieDetails(id: Int): Result<Movie> {
        return load({
            apiService.loadMovieById(id, "2BW30XT-0E84FXT-PC1P59Z-1BW2MWB")
        }, { dto: MovieDto? ->
            if (dto != null) {
                mapper.mapDtoToDomainModel(dto)
            } else {
                throw RuntimeException("movie by id $id not found")
            }
        })
    }

    override suspend fun getTrailers(movieId: Int): Result<List<Trailer>> {
        return load({
            apiService.loadTrailers(movieId, "2BW30XT-0E84FXT-PC1P59Z-1BW2MWB")
        }, { dto: TrailerListDto? ->
            dto?.videos?.trailers?.map { mapper.mapDtoToDomainModel(it) } ?: listOf()
        })
    }

    private suspend fun <DTO, DM> load(
        responseSupplier: suspend () -> Response<DTO>,
        mapper: (DTO?) -> DM
    ): Result<DM> {
        return try {
            val response: Response<DTO> = responseSupplier()
            if (response.isSuccessful) {
                val body: DTO? = response.body()
                Success(mapper(body))
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