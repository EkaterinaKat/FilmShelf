package com.katyshevtseva.filmshelf.data.remote.model

import com.google.gson.annotations.SerializedName

data class VideosDto(
    @SerializedName("trailers")
    val trailers: List<TrailerDto>
)