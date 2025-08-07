package com.katyshevtseva.filmshelf.data.repository

import com.katyshevtseva.filmshelf.domain.model.FiltersValues
import com.katyshevtseva.filmshelf.domain.repository.FiltersValuesRepository
import javax.inject.Inject

class FiltersValuesRepositoryImpl @Inject constructor() : FiltersValuesRepository {

    private var filtersValues = FiltersValues()

    override fun setFiltersValues(values: FiltersValues) {
        if (filtersValues != values) {
            filtersValues = values
        }
    }

    override fun getFiltersValues(): FiltersValues {
        return filtersValues
    }
}