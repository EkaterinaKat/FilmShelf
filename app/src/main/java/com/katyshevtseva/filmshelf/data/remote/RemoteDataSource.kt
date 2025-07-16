package com.katyshevtseva.filmshelf.data.remote

import com.katyshevtseva.filmshelf.data.remote.model.MovieDto
import com.katyshevtseva.filmshelf.data.remote.model.MovieListDto
import com.katyshevtseva.filmshelf.data.remote.model.PossibleValueDto
import com.katyshevtseva.filmshelf.data.remote.model.TrailerListDto
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun getFilteredMovies(
        page: Int,
        sortField: String,
        sortOrder: String,
        rating: String,
        limit: Int,
        type: String,
        year: String?,
        genre: String?,
        country: String?
    ): MovieListDto {
        return load {
            apiService.loadMovies(
                page,
                sortField,
                sortOrder,
                rating,
                limit,
                type,
                year,
                genre,
                country
            )
        }
    }

    suspend fun getFilteredMovies(
        page: Int,
        sortField: String,
        sortOrder: String,
        rating: String,
        limit: Int,
        type: String
    ): MovieListDto {
        return load {
            apiService.loadMovies(
                page,
                sortField,
                sortOrder,
                rating,
                limit,
                type,
                null,
                null,
                null
            )
        }
    }

    suspend fun getMovieDetails(kpId: Int): MovieDto {
        return load {
            apiService.loadMovieById(kpId)
        }
    }

    suspend fun getTrailers(movieKpId: Int): TrailerListDto {
        return load {
            apiService.loadTrailers(movieKpId)
        }
    }

    suspend fun searchMovieByTitle(searchString: String): MovieListDto {
        return load {
            apiService.searchByTitle(1, searchString)
        }
    }

    suspend fun getPossibleValue(field: String): List<PossibleValueDto> {
        return load {
            apiService.loadPossibleValue(field)
        }
    }

    private suspend fun <DTO> load(
        responseSupplier: suspend () -> Response<DTO>
    ): DTO {
        return try {
            val response: Response<DTO> = responseSupplier()
            val body: DTO? = response.body()
            if (response.isSuccessful && body != null) {
                body
            } else {
                throw RuntimeException("Ошибка сервера: ${response.code()}")
            }
        } catch (e: IOException) {
            throw RuntimeException("Ошибка сети: ${e.localizedMessage}", e)
        } catch (e: Exception) {
            throw RuntimeException("Неизвестная ошибка: ${e.localizedMessage}", e)
        }
    }
}