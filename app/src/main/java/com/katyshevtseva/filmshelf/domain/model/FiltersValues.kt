package com.katyshevtseva.filmshelf.domain.model

data class FiltersValues(
    var genre: Genre = Genre.emptyGenre,
    var country: Country = Country.emptyCountry,
    var yearRange: YearRange? = null,
    var ratingCategory: RatingCategory = RatingCategory.ALL
)