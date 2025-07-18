package com.katyshevtseva.filmshelf.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.katyshevtseva.filmshelf.domain.model.FiltersValues
import com.katyshevtseva.filmshelf.domain.model.MovieShortInfo
import com.katyshevtseva.filmshelf.domain.model.SortType
import com.katyshevtseva.filmshelf.domain.result.Error
import com.katyshevtseva.filmshelf.domain.result.Success
import com.katyshevtseva.filmshelf.domain.usecase.GetFilteredMoviesUseCase
import com.katyshevtseva.filmshelf.domain.usecase.GetFiltersValuesUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getFilteredMoviesUseCase: GetFilteredMoviesUseCase,
    private val getFiltersValuesUseCase: GetFiltersValuesUseCase
) : ViewModel() {

    private val _moviesLD = MutableLiveData<List<MovieShortInfo>>()
    val moviesLD: LiveData<List<MovieShortInfo>>
        get() = _moviesLD

    private val _errorLD = MutableLiveData<String>()
    val errorLD: LiveData<String>
        get() = _errorLD

    private val _loadingLD = MutableLiveData<Boolean>()
    val loadingLD: LiveData<Boolean>
        get() = _loadingLD

    val filtersValuesLD: LiveData<FiltersValues> = getFiltersValuesUseCase.invoke()

    private val filtersValues: FiltersValues
        get() = getFiltersValuesUseCase.invoke().value
            ?: throw RuntimeException("filters values should not be null")

    private var page = 0

    private var sortType = SortType.POPULAR_FIRST

    fun loadNextPage() {
        if (_loadingLD.value != true) {
            page++
            viewModelScope.launch {
                _loadingLD.value = true
                val result = getFilteredMoviesUseCase.invoke(page, sortType, filtersValues)
                when (result) {
                    is Success<List<MovieShortInfo>> -> {
                        addNextPageToExistingOnes(result.data)
                    }

                    is Error -> _errorLD.value = result.exception.message.toString()
                }
                _loadingLD.value = false
            }
        }
    }

    fun onFiltersValuesUpdate() {
        resetPagination()
        loadNextPage()
    }

    fun onSortTypeSelect(newSortType: SortType) {
        sortType = newSortType
        resetPagination()
        loadNextPage()
    }

    private fun resetPagination() {
        page = 0
        _moviesLD.value = listOf()
    }

    private fun addNextPageToExistingOnes(nextPage: List<MovieShortInfo>) {
        val existingPages = _moviesLD.value
        var allPages = mutableListOf<MovieShortInfo>()
        if (existingPages != null) {
            allPages.addAll(existingPages)
        }
        allPages.addAll(nextPage)
        _moviesLD.value = allPages
    }
}