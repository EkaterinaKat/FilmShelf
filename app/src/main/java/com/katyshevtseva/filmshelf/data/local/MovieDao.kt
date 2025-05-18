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
}