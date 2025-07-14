package com.katyshevtseva.filmshelf.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.katyshevtseva.filmshelf.domain.model.FiltersValues
import com.katyshevtseva.filmshelf.domain.repository.FiltersValuesRepository
import javax.inject.Inject

class FiltersValuesRepositoryImpl @Inject constructor() : FiltersValuesRepository {

    private val filtersValues = MutableLiveData<FiltersValues>(
        FiltersValues()
    )

    override fun setFiltersValues(values: FiltersValues) {
        if (filtersValues.value != values) {
            filtersValues.value = values
        }
    }

    override fun getFiltersValues(): LiveData<FiltersValues> {
        return filtersValues
    }
}