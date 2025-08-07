package com.katyshevtseva.filmshelf.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.katyshevtseva.filmshelf.R
import com.katyshevtseva.filmshelf.domain.model.Country
import com.katyshevtseva.filmshelf.domain.model.Genre
import com.katyshevtseva.filmshelf.domain.model.RatingCategory
import com.katyshevtseva.filmshelf.domain.model.YearRange
import com.katyshevtseva.filmshelf.domain.result.Error
import com.katyshevtseva.filmshelf.domain.result.Success
import com.katyshevtseva.filmshelf.domain.usecase.GetCountriesUseCase
import com.katyshevtseva.filmshelf.domain.usecase.GetFiltersValuesUseCase
import com.katyshevtseva.filmshelf.domain.usecase.GetGenresUseCase
import com.katyshevtseva.filmshelf.domain.usecase.GetYearSelectRangeUseCase
import com.katyshevtseva.filmshelf.domain.usecase.SetFiltersValuesUseCase
import com.katyshevtseva.filmshelf.presentation.util.SpinnerData
import com.katyshevtseva.filmshelf.presentation.util.YearSliderData
import com.katyshevtseva.filmshelf.presentation.util.getYearRangeString
import kotlinx.coroutines.launch
import javax.inject.Inject

class FiltersViewModel @Inject constructor(
    private val application: Application,
    private val getGenresUseCase: GetGenresUseCase,
    private val getCountriesUseCase: GetCountriesUseCase,
    private val getYearSelectRangeUseCase: GetYearSelectRangeUseCase,
    getFiltersValuesUseCase: GetFiltersValuesUseCase,
    private val setFiltersValuesUseCase: SetFiltersValuesUseCase
) : AndroidViewModel(application) {

    private val _genresLD = MutableLiveData<SpinnerData<Genre>>()
    val genresLD: LiveData<SpinnerData<Genre>>
        get() = _genresLD

    private val _countriesLD = MutableLiveData<SpinnerData<Country>>()
    val countriesLD: LiveData<SpinnerData<Country>>
        get() = _countriesLD

    private val _errorLD = MutableLiveData<String>()
    val errorLD: LiveData<String>
        get() = _errorLD

    private val _loadingLD = MutableLiveData<Boolean>()
    val loadingLD: LiveData<Boolean>
        get() = _loadingLD

    private val _yearSliderDataLD = MutableLiveData<YearSliderData>()
    val yearSliderDataLD: LiveData<YearSliderData>
        get() = _yearSliderDataLD

    private val _selectedYearRangeStringLD = MutableLiveData<String>()
    val selectedYearRangeStringLD: LiveData<String>
        get() = _selectedYearRangeStringLD

    private val _initRatingLD = MutableLiveData<RatingCategory>()
    val initRatingLD: LiveData<RatingCategory>
        get() = _initRatingLD

    private val _applyButtonTextLD = MutableLiveData<String>()
    val applyButtonTextLD: LiveData<String>
        get() = _applyButtonTextLD

    private val _shouldCloseActivity = MutableLiveData<Unit>()
    val shouldCloseActivity: LiveData<Unit>
        get() = _shouldCloseActivity

    private val initValues = getFiltersValuesUseCase.invoke()

    private val currentValues = initValues.copy()

    init {
        _loadingLD.value = true

        setApplyButtonText()
        _initRatingLD.value = initValues.ratingCategory

        val genresJob = viewModelScope.launch { loadGenres() }
        val countriesJob = viewModelScope.launch { loadCountries() }
        val yearsJob = viewModelScope.launch { loadYearRange() }

        viewModelScope.launch {
            genresJob.join()
            countriesJob.join()
            yearsJob.join()
            _loadingLD.value = false
        }
    }

    fun onGenreSelect(genre: Genre) {
        currentValues.genre = genre
        setApplyButtonText()
    }

    fun onCountrySelect(country: Country) {
        currentValues.country = country
        setApplyButtonText()
    }

    fun onYearRangeSelect(start: Int, end: Int) {
        _selectedYearRangeStringLD.value = getYearRangeString(start, end)

        val selectedRange = YearRange(start, end)
        currentValues.yearRange = if (selectedRange == yearSliderDataLD.value?.entireRange) {
            null
        } else {
            selectedRange
        }

        setApplyButtonText()
    }

    fun onRatingCategorySelect(category: RatingCategory) {
        currentValues.ratingCategory = category
        setApplyButtonText()
    }

    fun onApplyButtonClick() {
        setFiltersValuesUseCase.invoke(currentValues)
        _shouldCloseActivity.value = Unit
    }

    private fun setApplyButtonText() {
        _applyButtonTextLD.value = if (valuesHasBeenChanged()) {
            application.getString(R.string.apply)
        } else {
            application.getString(R.string.back)
        }
    }

    private fun valuesHasBeenChanged(): Boolean {
        return initValues != currentValues
    }

    private suspend fun loadGenres() {
        val result = getGenresUseCase.invoke()
        when (result) {
            is Success<List<Genre>> -> {
                _genresLD.value = SpinnerData(
                    listOf(Genre.emptyGenre) + result.data,
                    initValues.genre
                )
            }
            is Error -> _errorLD.value = result.exception.message.toString()
        }
    }

    private suspend fun loadCountries() {
        val result = getCountriesUseCase.invoke()
        when (result) {
            is Success<List<Country>> -> {
                _countriesLD.value = SpinnerData(
                    listOf(Country.emptyCountry) + result.data,
                    initValues.country
                )
            }
            is Error -> _errorLD.value = result.exception.message.toString()
        }
    }

    private suspend fun loadYearRange() {
        val result = getYearSelectRangeUseCase.invoke()
        when (result) {
            is Success<YearRange> -> {
                _yearSliderDataLD.value = YearSliderData(result.data, initValues.yearRange)
            }
            is Error -> _errorLD.value = result.exception.message.toString()
        }
    }
}