package com.katyshevtseva.filmshelf.domain.usecase

import com.katyshevtseva.filmshelf.domain.model.FiltersValues
import com.katyshevtseva.filmshelf.domain.repository.FiltersValuesRepository
import javax.inject.Inject

class SetFiltersValuesUseCase @Inject constructor(
    private val filtersValuesRepository: FiltersValuesRepository
) {

    operator fun invoke(values: FiltersValues) {
        filtersValuesRepository.setFiltersValues(values)
    }
}