package com.katyshevtseva.filmshelf.presentation.util

data class SpinnerData<T>(
    val items: List<T>,
    val initItem: T? = null
)