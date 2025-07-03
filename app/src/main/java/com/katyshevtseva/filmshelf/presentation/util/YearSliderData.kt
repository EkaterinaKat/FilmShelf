package com.katyshevtseva.filmshelf.presentation.util

import com.katyshevtseva.filmshelf.domain.model.YearRange

data class YearSliderData(
    val entireRange: YearRange,
    val initialRange: YearRange? = null
)