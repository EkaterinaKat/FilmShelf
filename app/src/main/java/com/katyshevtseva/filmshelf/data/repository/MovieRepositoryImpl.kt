package com.katyshevtseva.filmshelf.data.repository

import com.katyshevtseva.filmshelf.data.local.LocalDataSource
import com.katyshevtseva.filmshelf.data.mapper.MovieMapper
import com.katyshevtseva.filmshelf.data.remote.RemoteDataSource
import com.katyshevtseva.filmshelf.data.utils.getCountryForWepRequest
import com.katyshevtseva.filmshelf.data.utils.getGenreForWepRequest
import com.katyshevtseva.filmshelf.data.utils.getRatingStringForWebRequest
import com.katyshevtseva.filmshelf.data.utils.getSortFieldString
import com.katyshevtseva.filmshelf.data.utils.getSortOrderString
import com.katyshevtseva.filmshelf.data.utils.getYearRangeStringForWebRequest
import com.katyshevtseva.filmshelf.domain.model.Country
import com.katyshevtseva.filmshelf.domain.model.FiltersValues
import com.katyshevtseva.filmshelf.domain.model.Genre
import com.katyshevtseva.filmshelf.domain.model.Movie
import com.katyshevtseva.filmshelf.domain.model.MovieShortInfo
import com.katyshevtseva.filmshelf.domain.model.RatingCategory
import com.katyshevtseva.filmshelf.domain.model.SortType
import com.katyshevtseva.filmshelf.domain.model.SortType.OLD_FIRST
import com.katyshevtseva.filmshelf.domain.model.Trailer
import com.katyshevtseva.filmshelf.domain.model.YearRange
import com.katyshevtseva.filmshelf.domain.repository.MovieRepository
import com.katyshevtseva.filmshelf.domain.result.Error
import com.katyshevtseva.filmshelf.domain.result.Result
import com.katyshevtseva.filmshelf.domain.result.Success
import java.util.Calendar
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val mapper: MovieMapper
) : MovieRepository {

    override suspend fun getFilteredMovies(
        page: Int,
        sortType: SortType,
        filtersValues: FiltersValues
    ): Result<List<MovieShortInfo>> {

        return try {
            val response = remoteDataSource.getFilteredMovies(
                page,
                getSortFieldString(sortType),
                getSortOrderString(sortType),
                getRatingStringForWebRequest(filtersValues.ratingCategory),
                PAGE_SIZE,
                MOVIE_TYPE,
                getYearRangeStringForWebRequest(filtersValues.yearRange),
                getGenreForWepRequest(filtersValues.genre),
                getCountryForWepRequest(filtersValues.country)
            )
            Success(response.movies.map {
                mapper.mapDtoToDomainModel(it)
            })
        } catch (e: Exception) {
            Error(e)
        }
    }

    override suspend fun getMovieDetails(kpId: Int): Result<Movie> {
        val movieFromLocalStorage = localDataSource.findByKpId(kpId)
        if (movieFromLocalStorage != null) {
            return Success(mapper.mapEntityToDomainModel(movieFromLocalStorage))
        }
        return try {
            Success(mapper.mapDtoToDomainModel(remoteDataSource.getMovieDetails(kpId)))
        } catch (e: Exception) {
            Error(e)
        }
    }

    override suspend fun getTrailers(movieKpId: Int): Result<List<Trailer>> {
        return try {
            val trailerList: List<Trailer> =
                remoteDataSource.getTrailers(movieKpId).videos?.trailers?.map {
                    mapper.mapDtoToDomainModel(it)
                } ?: listOf()
            Success(trailerList)
        } catch (e: Exception) {
            Error(e)
        }
    }

    override suspend fun saveFavouriteMovie(movie: Movie) {
        if (!isMovieFavourite(movie.kpId)) {
            localDataSource.add(mapper.mapDomainModelToEntity(movie))
        }
    }

    override suspend fun deleteFromFavouriteMovies(kpId: Int) {
        localDataSource.deleteByKpId(kpId)
    }

    override suspend fun getFavouriteMovies(): List<MovieShortInfo> {
        return localDataSource.getMovies().map { mapper.mapEntityToMovieShortInfo(it) }
    }

    override suspend fun isMovieFavourite(kpId: Int): Boolean {
        return localDataSource.existsByKpId(kpId)
    }

    override suspend fun searchMovieByTitle(searchString: String): Result<List<MovieShortInfo>> {
        return try {
            Success(remoteDataSource.searchMovieByTitle(searchString).movies.map {
                mapper.mapDtoToDomainModel(it)
            })
        } catch (e: Exception) {
            Error(e)
        }
    }

    override suspend fun getGenres(): Result<List<Genre>> {
        return try {
            Success(remoteDataSource.getPossibleValue(GENRE_FIELD).map {
                mapper.mapPossibleValueToDomainGenre(it)
            })
        } catch (e: Exception) {
            Error(e)
        }
    }

    override suspend fun getCountries(): Result<List<Country>> {
        return try {
            Success(remoteDataSource.getPossibleValue(COUNTRY_FIELD).map {
                mapper.mapPossibleValueToDomainCountry(it)
            })
        } catch (e: Exception) {
            Error(e)
        }
    }

    override suspend fun getYearSelectRange(): Result<YearRange> {
        return try {
            val response = remoteDataSource.getFilteredMovies(
                1,
                getSortFieldString(OLD_FIRST),
                getSortOrderString(OLD_FIRST),
                getRatingStringForWebRequest(RatingCategory.ALL),
                1,
                MOVIE_TYPE
            )

            Success(
                YearRange(
                    response.movies[0].year
                        ?: throw RuntimeException("unable to get year of oldest movie"),
                    Calendar.getInstance().get(Calendar.YEAR)
                )
            )
        } catch (e: Exception) {
            Error(e)
        }
    }

    companion object {
        const val MOVIE_TYPE = "movie"
        const val GENRE_FIELD = "genres.name"
        const val COUNTRY_FIELD = "countries.name"
        const val PAGE_SIZE = 20
    }
}