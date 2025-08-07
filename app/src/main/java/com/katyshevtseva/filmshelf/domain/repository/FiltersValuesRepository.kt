package com.katyshevtseva.filmshelf.domain.repository

import com.katyshevtseva.filmshelf.domain.model.FiltersValues

interface FiltersValuesRepository {

    fun setFiltersValues(values: FiltersValues)

    fun getFiltersValues(): FiltersValues
}