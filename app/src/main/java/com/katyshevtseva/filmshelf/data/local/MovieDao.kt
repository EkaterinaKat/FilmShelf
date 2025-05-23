package com.katyshevtseva.filmshelf.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.katyshevtseva.filmshelf.data.local.entity.MovieEntity

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun add(movie: MovieEntity)

    @Query("SELECT * FROM movie")
    suspend fun getMovies(): List<MovieEntity>

    @Query("DELETE FROM movie WHERE kpId = :kpId")
    suspend fun deleteByKpId(kpId: Int)

    @Query("SELECT EXISTS(SELECT 1 FROM movie WHERE kpId = :kpId)")
    suspend fun existsByKpId(kpId: Int): Boolean

    @Query("SELECT * FROM movie WHERE kpId = :kpId LIMIT 1")
    suspend fun findByKpId(kpId: Int): MovieEntity?
}