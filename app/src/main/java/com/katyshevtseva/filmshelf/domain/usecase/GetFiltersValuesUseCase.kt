package com.katyshevtseva.filmshelf.domain.usecase

import com.katyshevtseva.filmshelf.domain.model.FiltersValues
import com.katyshevtseva.filmshelf.domain.repository.FiltersValuesRepository
import javax.inject.Inject

class GetFiltersValuesUseCase @Inject constructor(
    private val filtersValuesRepository: FiltersValuesRepository
) {

    operator fun invoke(): FiltersValues {
        return filtersValuesRepository.getFiltersValues()
    }
}