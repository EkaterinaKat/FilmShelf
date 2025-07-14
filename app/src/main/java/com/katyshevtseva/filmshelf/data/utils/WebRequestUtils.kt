package com.katyshevtseva.filmshelf.data.utils

import com.katyshevtseva.filmshelf.domain.model.Country
import com.katyshevtseva.filmshelf.domain.model.Genre
import com.katyshevtseva.filmshelf.domain.model.RatingCategory
import com.katyshevtseva.filmshelf.domain.model.SortType
import com.katyshevtseva.filmshelf.domain.model.SortType.HIGH_RATING_FIRST
import com.katyshevtseva.filmshelf.domain.model.SortType.NEW_FIRST
import com.katyshevtseva.filmshelf.domain.model.SortType.OLD_FIRST
import com.katyshevtseva.filmshelf.domain.model.SortType.POPULAR_FIRST
import com.katyshevtseva.filmshelf.domain.model.YearRange

fun getRatingStringForWebRequest(ratingCategory: RatingCategory): String {
    return "${ratingCategory.lowerLimit}-${ratingCategory.upperLimit}"
}

fun getYearRangeStringForWebRequest(yearRange: YearRange?): String? {
    return if (yearRange == null) {
        null
    } else {
        "${yearRange.start}-${yearRange.end}"
    }
}

fun getGenreForWepRequest(genre: Genre): String? {
    return if (genre.isEmpty)
        null
    else
        genre.name
}

fun getCountryForWepRequest(country: Country): String? {
    return if (country.isEmpty)
        null
    else
        country.name
}

fun getSortFieldString(sortType: SortType): String {
    return when (sortType) {
        POPULAR_FIRST -> "votes.kp"
        HIGH_RATING_FIRST -> "rating.kp"
        NEW_FIRST, OLD_FIRST -> "year"
    }
}

fun getSortOrderString(sortType: SortType): String {
    return when (sortType) {
        POPULAR_FIRST, HIGH_RATING_FIRST, NEW_FIRST -> "-1"
        OLD_FIRST -> "1"
    }
}