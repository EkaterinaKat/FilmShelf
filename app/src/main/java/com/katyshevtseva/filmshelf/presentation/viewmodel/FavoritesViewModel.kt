package com.katyshevtseva.filmshelf.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.katyshevtseva.filmshelf.domain.model.MovieShortInfo
import com.katyshevtseva.filmshelf.domain.usecase.GetFavouriteMoviesUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoritesViewModel @Inject constructor(
    private val getFavouriteMoviesUseCase: GetFavouriteMoviesUseCase
) : ViewModel() {

    private val _moviesLD = MutableLiveData<List<MovieShortInfo>>()
    val moviesLD: LiveData<List<MovieShortInfo>>
        get() = _moviesLD

    fun loadMovies() {
        viewModelScope.launch {
            _moviesLD.value = getFavouriteMoviesUseCase.invoke()
        }
    }
}