package com.katyshevtseva.filmshelf.domain.repository

import com.katyshevtseva.filmshelf.domain.model.Country
import com.katyshevtseva.filmshelf.domain.model.FiltersValues
import com.katyshevtseva.filmshelf.domain.model.Genre
import com.katyshevtseva.filmshelf.domain.model.Movie
import com.katyshevtseva.filmshelf.domain.model.MovieShortInfo
import com.katyshevtseva.filmshelf.domain.model.SortType
import com.katyshevtseva.filmshelf.domain.model.Trailer
import com.katyshevtseva.filmshelf.domain.model.YearRange
import com.katyshevtseva.filmshelf.domain.result.Result

interface MovieRepository {

    suspend fun getFilteredMovies(
        page: Int,
        sortType: SortType,
        filtersValues: FiltersValues
    ): Result<List<MovieShortInfo>>

    suspend fun getMovieDetails(kpId: Int): Result<Movie>

    suspend fun getTrailers(movieKpId: Int): Result<List<Trailer>>

    suspend fun saveFavouriteMovie(movie: Movie)

    suspend fun deleteFromFavouriteMovies(kpId: Int)

    suspend fun getFavouriteMovies(): List<MovieShortInfo>

    suspend fun isMovieFavourite(kpId: Int): Boolean

    suspend fun searchMovieByTitle(searchString: String): Result<List<MovieShortInfo>>

    suspend fun getGenres(): Result<List<Genre>>

    suspend fun getCountries(): Result<List<Country>>

    suspend fun getYearSelectRange(): Result<YearRange>
}