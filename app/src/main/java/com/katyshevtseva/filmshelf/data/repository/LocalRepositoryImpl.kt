package com.katyshevtseva.filmshelf.data.repository

import com.katyshevtseva.filmshelf.data.local.MovieDao
import com.katyshevtseva.filmshelf.data.mapper.MovieMapper
import com.katyshevtseva.filmshelf.domain.model.Movie
import com.katyshevtseva.filmshelf.domain.repository.LocalRepository
import javax.inject.Inject

class LocalRepositoryImpl @Inject constructor(
    private val movieDao: MovieDao,
    private val mapper: MovieMapper
) : LocalRepository {

    override suspend fun saveFavouriteMovie(movie: Movie) {
        movieDao.add(mapper.mapDomainModelToEntity(movie))
    }

    override suspend fun deleteFromFavouriteMovies(movie: Movie) {
        movieDao.deleteByKpId(movie.kpId)
    }

    override suspend fun getFavouriteMovies(): List<Movie> {
        return movieDao.getMovies().map { mapper.mapEntityToDomainModel(it) }
    }
}