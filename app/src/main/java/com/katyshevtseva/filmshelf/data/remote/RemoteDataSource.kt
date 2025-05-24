package com.katyshevtseva.filmshelf.data.remote

import com.katyshevtseva.filmshelf.data.remote.model.MovieDto
import com.katyshevtseva.filmshelf.data.remote.model.MovieListDto
import com.katyshevtseva.filmshelf.data.remote.model.TrailerListDto
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun getBestMovies(page: Int): MovieListDto {
        return load {
            apiService.loadMovies(
                page,
                "2BW30XT-0E84FXT-PC1P59Z-1BW2MWB",
                "votes.kp",
                "-1",
                "3-10",
                "20"
            )
        }
    }

    suspend fun getMovieDetails(kpId: Int): MovieDto {
        return load {
            apiService.loadMovieById(kpId, "2BW30XT-0E84FXT-PC1P59Z-1BW2MWB")
        }
    }

    suspend fun getTrailers(movieKpId: Int): TrailerListDto {
        return load {
            apiService.loadTrailers(movieKpId, "2BW30XT-0E84FXT-PC1P59Z-1BW2MWB")
        }
    }

    suspend fun searchMovieByTitle(searchString: String): MovieListDto {
        return load {
            apiService.searchByTitle(1, searchString, "2BW30XT-0E84FXT-PC1P59Z-1BW2MWB")
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