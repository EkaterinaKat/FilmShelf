package com.katyshevtseva.filmshelf.domain.usecase

import androidx.lifecycle.LiveData
import com.katyshevtseva.filmshelf.domain.model.FiltersValues
import com.katyshevtseva.filmshelf.domain.repository.FiltersValuesRepository
import javax.inject.Inject

class GetFiltersValuesUseCase @Inject constructor(
    private val filtersValuesRepository: FiltersValuesRepository
) {

    operator fun invoke(): LiveData<FiltersValues> {
        return filtersValuesRepository.getFiltersValues()
    }
}