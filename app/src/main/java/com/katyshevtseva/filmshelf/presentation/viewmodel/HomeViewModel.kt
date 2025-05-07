package com.katyshevtseva.filmshelf.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.katyshevtseva.filmshelf.domain.model.Movie
import com.katyshevtseva.filmshelf.domain.result.Error
import com.katyshevtseva.filmshelf.domain.result.Success
import com.katyshevtseva.filmshelf.domain.usecase.GetBestMoviesUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getBestMoviesUseCase: GetBestMoviesUseCase
) : ViewModel() {

    private val _moviesLD = MutableLiveData<List<Movie>>()
    val moviesLD: LiveData<List<Movie>>
        get() = _moviesLD

    private val _errorLD = MutableLiveData<String>()
    val errorLD: LiveData<String>
        get() = _errorLD

    private val _loadingLD = MutableLiveData<Boolean>()
    val loadingLD: LiveData<Boolean>
        get() = _loadingLD

    init {
        viewModelScope.launch {
            _loadingLD.value = true
            val result = getBestMoviesUseCase.invoke()
            when (result) {
                is Success<List<Movie>> -> _moviesLD.value = result.data
                is Error -> _errorLD.value = result.exception.message.toString()
            }
            _loadingLD.value = false
        }
    }
}