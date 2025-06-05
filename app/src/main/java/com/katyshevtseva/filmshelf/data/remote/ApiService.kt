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
        @Query("token") token: String,
        @Query("sortField") sortField: String,
        @Query("sortType") sortType: String,
        @Query("rating.kp") ratingKp: String,
        @Query("limit") limit: Int,
        @Query("type") type: String
    ): Response<MovieListDto>

    @GET("v1.4/movie/{movieId}")
    suspend fun loadMovieById(
        @Path("movieId") movieId: Int,
        @Query("token") token: String
    ): Response<MovieDto>

    @GET("v1.4/movie/{movieId}")
    suspend fun loadTrailers(
        @Path("movieId") movieId: Int,
        @Query("token") token: String
    ): Response<TrailerListDto>

    @GET("v1.4/movie/search")
    suspend fun searchByTitle(
        @Query("page") page: Int,
        @Query("query") query: String,
        @Query("token") token: String
    ): Response<MovieListDto>

    @GET("v1/movie/possible-values-by-field")
    suspend fun loadPossibleValue(
        @Query("field") field: String,
        @Query("token") token: String
    ): Response<List<PossibleValueDto>>
}