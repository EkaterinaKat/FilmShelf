package com.katyshevtseva.filmshelf.web

import com.katyshevtseva.filmshelf.web.model.MovieResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("movie")
    fun loadMovies1(
        @Query("page") page: Int,
        @Query("token") token: String,
        @Query("sortField") sortField: String,
        @Query("sortType") sortType: String,
        @Query("rating.kp") ratingKp: String,
        @Query("limit") limit: String,

        ): Single<MovieResponse>
}