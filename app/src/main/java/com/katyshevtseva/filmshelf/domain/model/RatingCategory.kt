package com.katyshevtseva.filmshelf.domain.model

enum class RatingCategory(
    val id: Int,
    val lowerLimit: Int,
    val upperLimit: Int,
    val numRepresentation: String
) {
    GOOD(1, 6, 10, "6+"),
    BEST(2, 8, 10, "8+"),
    ALL(3, 3, 10, "");

    companion object {
        fun getById(id: Int): RatingCategory {
            for (category in RatingCategory.entries) {
                if (category.id == id) (
                        return category
                        )
            }
            throw RuntimeException("Unknown rating category id")
        }
    }
}