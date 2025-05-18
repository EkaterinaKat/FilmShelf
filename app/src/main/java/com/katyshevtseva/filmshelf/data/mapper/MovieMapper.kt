package com.katyshevtseva.filmshelf.data.mapper

import com.katyshevtseva.filmshelf.data.local.entity.MovieEntity
import com.katyshevtseva.filmshelf.data.remote.model.MovieDto
import com.katyshevtseva.filmshelf.data.remote.model.TrailerDto
import com.katyshevtseva.filmshelf.domain.model.Movie
import com.katyshevtseva.filmshelf.domain.model.Trailer
import javax.inject.Inject

class MovieMapper @Inject constructor() {

    fun mapDtoToDomainModel(dto: MovieDto) = Movie(
        kpId = dto.id,
        name = dto.name,
        description = dto.description,
        year = dto.year,
        posterUrl = dto.poster?.url,
        ratingKp = dto.rating?.kp
    )

    fun mapDtoToDomainModel(dto: TrailerDto) = Trailer(
        name = dto.name,
        url = dto.url
    )

    fun mapDomainModelToEntity(movie: Movie) = MovieEntity(
        kpId = movie.kpId,
        name = movie.name,
        description = movie.description,
        year = movie.year,
        posterUrl = movie.posterUrl,
        ratingKp = movie.ratingKp
    )

    fun mapEntityToDomainModel(entity: MovieEntity) = Movie(
        kpId = entity.kpId,
        name = entity.name,
        description = entity.description,
        year = entity.year,
        posterUrl = entity.posterUrl,
        ratingKp = entity.ratingKp
    )
}