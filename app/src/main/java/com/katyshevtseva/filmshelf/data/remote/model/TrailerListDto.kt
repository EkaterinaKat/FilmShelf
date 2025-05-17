package com.katyshevtseva.filmshelf.data.remote.model

import com.google.gson.annotations.SerializedName

data class TrailerListDto(
    @SerializedName("videos")
    val videos: VideosDto? = null
)