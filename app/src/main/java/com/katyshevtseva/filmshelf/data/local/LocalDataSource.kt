package com.katyshevtseva.filmshelf.data.local

import com.katyshevtseva.filmshelf.data.local.entity.MovieEntity
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val movieDao: MovieDao
) {

    suspend fun add(movie: MovieEntity) {
        movieDao.add(movie)
    }

    suspend fun deleteByKpId(kpId: Int) {
        movieDao.deleteByKpId(kpId)
    }

    suspend fun getMovies(): List<MovieEntity> {
        return movieDao.getMovies()
    }

    suspend fun existsByKpId(kpId: Int): Boolean {
        return movieDao.existsByKpId(kpId)
    }
}