package com.katyshevtseva.filmshelf.data.remote

import com.katyshevtseva.filmshelf.data.remote.model.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("movie")
    suspend fun loadMovies(
        @Query("page") page: Int,
        @Query("token") token: String,
        @Query("sortField") sortField: String,
        @Query("sortType") sortType: String,
        @Query("rating.kp") ratingKp: String,
        @Query("limit") limit: String
    ): Response<MovieResponse>
}