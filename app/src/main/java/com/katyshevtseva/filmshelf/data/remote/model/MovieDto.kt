package com.katyshevtseva.filmshelf.data.remote.model

import com.google.gson.annotations.SerializedName

data class MovieDto(
    @SerializedName("id")
    val id: Int = 0,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("description")
    val description: String? = null,

    @SerializedName("year")
    val year: Int? = null,

    @SerializedName("poster")
    val poster: PosterDto? = null,

    @SerializedName("rating")
    val rating: RatingDto? = null
)