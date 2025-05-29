package com.katyshevtseva.filmshelf.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.katyshevtseva.filmshelf.domain.model.Country
import com.katyshevtseva.filmshelf.domain.model.Genre
import com.katyshevtseva.filmshelf.domain.result.Error
import com.katyshevtseva.filmshelf.domain.result.Success
import com.katyshevtseva.filmshelf.domain.usecase.GetCountriesUseCase
import com.katyshevtseva.filmshelf.domain.usecase.GetGenresUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class FiltersViewModel @Inject constructor(
    private val getGenresUseCase: GetGenresUseCase,
    private val getCountriesUseCase: GetCountriesUseCase
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

    init {
        _loadingLD.value = true

        val genresJob = viewModelScope.launch { loadGenres() }
        val countriesJob = viewModelScope.launch { loadCountries() }

        viewModelScope.launch {
            genresJob.join()
            countriesJob.join()
            _loadingLD.value = false
        }
    }

    fun onGenreSelect(genre: Genre) {

    }

    fun onCountrySelect(country: Country) {

    }

    private suspend fun loadGenres() {
        val result = getGenresUseCase.invoke()
        when (result) {
            is Success<List<Genre>> -> _genresLD.value = result.data
            is Error -> _errorLD.value = result.exception.message.toString()
        }
    }

    private suspend fun loadCountries() {
        val result = getCountriesUseCase.invoke()
        when (result) {
            is Success<List<Country>> -> _countriesLD.value = result.data
            is Error -> _errorLD.value = result.exception.message.toString()
        }
    }
}