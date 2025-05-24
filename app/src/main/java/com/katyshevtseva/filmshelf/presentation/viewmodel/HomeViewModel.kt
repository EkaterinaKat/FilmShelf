package com.katyshevtseva.filmshelf.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.katyshevtseva.filmshelf.domain.model.MovieShortInfo
import com.katyshevtseva.filmshelf.domain.result.Error
import com.katyshevtseva.filmshelf.domain.result.Success
import com.katyshevtseva.filmshelf.domain.usecase.GetBestMoviesUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getBestMoviesUseCase: GetBestMoviesUseCase
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

    private var page = 0

    private var searchJob: Job? = null
    private var searchString: String? = null

    init {
        loadNextPage()
    }

    fun loadNextPage() {
        if (_loadingLD.value == null || _loadingLD.value == false) {
            page++
            viewModelScope.launch {
                _loadingLD.value = true
                val result = getBestMoviesUseCase.invoke(page, searchString)
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

    private fun addNextPageToExistingOnes(nextPage: List<MovieShortInfo>) {
        val existingPages = _moviesLD.value
        var allPages = mutableListOf<MovieShortInfo>()
        if (existingPages != null) {
            allPages.addAll(existingPages)
        }
        allPages.addAll(nextPage)
        _moviesLD.value = allPages
    }

    fun onSearchInput(input: CharSequence?) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500)
            input?.let {
                searchString = it.toString()
                resetPagination()
                loadNextPage()
            }
        }
    }

    fun resetPagination() {
        page = 0
        _moviesLD.value = listOf()
    }
}