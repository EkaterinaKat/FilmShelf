package com.katyshevtseva.filmshelf.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.katyshevtseva.filmshelf.domain.model.MovieShortInfo
import com.katyshevtseva.filmshelf.domain.result.Error
import com.katyshevtseva.filmshelf.domain.result.Success
import com.katyshevtseva.filmshelf.domain.usecase.SearchMovieUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val searchMovieUseCase: SearchMovieUseCase
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

    private var searchJob: Job? = null

    fun loadMovies(searchString: String) {
        if (_loadingLD.value != true) {
            viewModelScope.launch {
                _loadingLD.value = true
                val result = searchMovieUseCase.invoke(searchString)
                when (result) {
                    is Success<List<MovieShortInfo>> -> {
                        _moviesLD.value = result.data
                    }

                    is Error -> _errorLD.value = result.exception.message.toString()
                }
                _loadingLD.value = false
            }
        }
    }

    fun onSearchInput(input: CharSequence?) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500)
            input?.let {
                loadMovies(it.toString())
            }
        }
    }
}