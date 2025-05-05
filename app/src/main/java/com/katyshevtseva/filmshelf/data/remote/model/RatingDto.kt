package com.katyshevtseva.filmshelf.data.remote.model

import com.google.gson.annotations.SerializedName

data class RatingDto(

    @SerializedName("kp")
    val kp: Double? = null,

    @SerializedName("imdb")
    val imdb: String? = null
)
