package com.katyshevtseva.filmshelf.data.remote.model

import com.google.gson.annotations.SerializedName

data class PossibleValueDto(

    @SerializedName("name")
    val name: String,

    @SerializedName("slug")
    val slug: String
)