package com.katyshevtseva.filmshelf.domain.model

data class FiltersValues(
    var genre: Genre = Genre.emptyGenre,
    var country: Country = Country.emptyCountry,
    var yearRange: YearRange? = YearRange(2001, 2009),
    var ratingCategory: RatingCategory = RatingCategory.ALL
)