package com.katyshevtseva.filmshelf.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.katyshevtseva.filmshelf.domain.model.Country
import com.katyshevtseva.filmshelf.domain.model.Genre
import com.katyshevtseva.filmshelf.domain.model.RatingCategory
import com.katyshevtseva.filmshelf.domain.model.YearRange
import com.katyshevtseva.filmshelf.domain.result.Error
import com.katyshevtseva.filmshelf.domain.result.Success
import com.katyshevtseva.filmshelf.domain.usecase.GetCountriesUseCase
import com.katyshevtseva.filmshelf.domain.usecase.GetGenresUseCase
import com.katyshevtseva.filmshelf.domain.usecase.GetYearSelectRangeUseCase
import com.katyshevtseva.filmshelf.presentation.util.getYearRangeString
import kotlinx.coroutines.launch
import javax.inject.Inject

class FiltersViewModel @Inject constructor(
    private val getGenresUseCase: GetGenresUseCase,
    private val getCountriesUseCase: GetCountriesUseCase,
    private val getYearSelectRangeUseCase: GetYearSelectRangeUseCase
) : ViewModel() {

    private val _genresLD = MutableLiveData<List<Genre>>()
    val genresLD: LiveData<List<Genre>>
        get() = _genresLD

    private val _countriesLD = MutableLiveData<List<Country>>()
    val countriesLD: LiveData<List<Country>>
        get() = _countriesLD

    private val _errorLD = MutableLiveData<String>()
    val errorLD: LiveData<String>
        get() = _errorLD

    private val _loadingLD = MutableLiveData<Boolean>()
    val loadingLD: LiveData<Boolean>
        get() = _loadingLD

    private val _entireYearRangeLD = MutableLiveData<YearRange>()
    val entireYearRangeLD: LiveData<YearRange>
        get() = _entireYearRangeLD

    private val _selectedYearRangeStringLD = MutableLiveData<String>()
    val selectedYearRangeStringLD: LiveData<String>
        get() = _selectedYearRangeStringLD

    init {
        _loadingLD.value = true

        val genresJob = viewModelScope.launch { loadGenres() }
        val countriesJob = viewModelScope.launch { loadCountries() }
        val yearsJob = viewModelScope.launch { loadEntireYearRange() }

        viewModelScope.launch {
            genresJob.join()
            countriesJob.join()
            yearsJob.join()
            _loadingLD.value = false
        }
    }

    fun onGenreSelect(genre: Genre) {
        Log.i("tag123456", "onGenreSelect ${genre.name}")
    }

    fun onCountrySelect(country: Country) {
        Log.i("tag123456", "onCountrySelect ${country.name}")
    }

    fun onYearRangeSelect(start: Int, end: Int) {
        _selectedYearRangeStringLD.value = getYearRangeString(start, end)
    }

    fun onRatingCategorySelect(category: RatingCategory) {
        Log.i("tag123456", "onRatingCategorySelect ${category.name}")
    }

    private suspend fun loadGenres() {
        val result = getGenresUseCase.invoke()
        when (result) {
            is Success<List<Genre>> -> _genresLD.value = listOf(Genre.emptyGenre) + result.data
            is Error -> _errorLD.value = result.exception.message.toString()
        }
    }

    private suspend fun loadCountries() {
        val result = getCountriesUseCase.invoke()
        when (result) {
            is Success<List<Country>> -> _countriesLD.value =
                listOf(Country.emptyCountry) + result.data
            is Error -> _errorLD.value = result.exception.message.toString()
        }
    }

    private suspend fun loadEntireYearRange() {
        val result = getYearSelectRangeUseCase.invoke()
        when (result) {
            is Success<YearRange> -> _entireYearRangeLD.value = result.data
            is Error -> _errorLD.value = result.exception.message.toString()
        }
    }
}