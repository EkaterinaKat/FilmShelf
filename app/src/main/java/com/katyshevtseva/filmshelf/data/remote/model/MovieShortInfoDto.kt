package com.katyshevtseva.filmshelf.data.remote.model

import com.google.gson.annotations.SerializedName

data class MovieShortInfoDto(
    @SerializedName("id")
    val kpId: Int = 0,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("poster")
    val poster: PosterDto? = null,

    @SerializedName("rating")
    val rating: RatingDto? = null,

    @SerializedName("year")
    val year: Int? = null
)