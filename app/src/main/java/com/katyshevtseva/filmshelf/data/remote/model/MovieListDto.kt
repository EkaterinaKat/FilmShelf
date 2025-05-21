package com.katyshevtseva.filmshelf.data.remote.model

import com.google.gson.annotations.SerializedName

data class MovieListDto(
    @SerializedName("docs")
    val movies: List<MovieShortInfoDto>? = null
)