package com.katyshevtseva.filmshelf.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie")
class MovieEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val kpId: Int,
    var name: String?,
    var description: String?,
    val year: Int?,
    val posterUrl: String?,
    val ratingKp: Double?
)