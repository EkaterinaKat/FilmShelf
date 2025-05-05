package com.katyshevtseva.filmshelf.data.remote.model

import com.google.gson.annotations.SerializedName

data class PosterDto(

    @SerializedName("url")
    val url: String? = null
)
