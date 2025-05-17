package com.katyshevtseva.filmshelf.data.mapper

import com.katyshevtseva.filmshelf.data.remote.model.MovieDto
import com.katyshevtseva.filmshelf.data.remote.model.TrailerDto
import com.katyshevtseva.filmshelf.domain.model.Movie
import com.katyshevtseva.filmshelf.domain.model.Trailer
import javax.inject.Inject

class MovieMapper @Inject constructor() {

    fun mapDtoToDomainModel(dto: MovieDto) = Movie(
        id = dto.id,
        name = dto.name,
        description = dto.description,
        year = dto.year,
        posterUrl = dto.poster?.url,
        ratingKp = dto.rating?.kp ?: 0.0
    )

    fun mapDtoToDomainModel(dto: TrailerDto) = Trailer(
        name = dto.name,
        url = dto.url
    )
}