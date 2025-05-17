package com.katyshevtseva.filmshelf.data.remote.model

import com.google.gson.annotations.SerializedName

data class TrailerDto(
    @SerializedName("name")
    val name: String? = null,

    @SerializedName("url")
    val url: String? = null
)