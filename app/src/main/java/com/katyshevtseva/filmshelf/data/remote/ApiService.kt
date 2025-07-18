package com.katyshevtseva.filmshelf.data.remote

import com.katyshevtseva.filmshelf.data.remote.model.MovieDto
import com.katyshevtseva.filmshelf.data.remote.model.MovieListDto
import com.katyshevtseva.filmshelf.data.remote.model.PossibleValueDto
import com.katyshevtseva.filmshelf.data.remote.model.TrailerListDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("v1.4/movie")
    suspend fun loadMovies(
        @Query("page") page: Int,
        @Query("sortField") sortField: String,
        @Query("sortType") sortType: String,
        @Query("rating.kp") ratingKp: String,
        @Query("limit") limit: Int,
        @Query("type") type: String,
        @Query("year") year: String?,
        @Query("genres.name") genre: String?,
        @Query("countries.name") country: String?,
    ): Response<MovieListDto>

    @GET("v1.4/movie/{movieId}")
    suspend fun loadMovieById(
        @Path("movieId") movieId: Int
    ): Response<MovieDto>

    @GET("v1.4/movie/{movieId}")
    suspend fun loadTrailers(
        @Path("movieId") movieId: Int
    ): Response<TrailerListDto>

    @GET("v1.4/movie/search")
    suspend fun searchByTitle(
        @Query("page") page: Int,
        @Query("query") query: String
    ): Response<MovieListDto>

    @GET("v1/movie/possible-values-by-field")
    suspend fun loadPossibleValue(
        @Query("field") field: String
    ): Response<List<PossibleValueDto>>
}