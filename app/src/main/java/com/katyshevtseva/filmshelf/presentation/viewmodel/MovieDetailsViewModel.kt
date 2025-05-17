package com.katyshevtseva.filmshelf.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.katyshevtseva.filmshelf.domain.model.Movie
import com.katyshevtseva.filmshelf.domain.model.Trailer
import com.katyshevtseva.filmshelf.domain.result.Error
import com.katyshevtseva.filmshelf.domain.result.Success
import com.katyshevtseva.filmshelf.domain.usecase.GetMovieDetailsUseCase
import com.katyshevtseva.filmshelf.domain.usecase.GetTrailersUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class MovieDetailsViewModel @AssistedInject constructor(
    @Assisted private val movieId: Int,
    private val getMovieDetailsUc: GetMovieDetailsUseCase,
    private val getTrailersUc: GetTrailersUseCase
) : ViewModel() {

    private val _movieLD = MutableLiveData<Movie>()
    val movieLD: LiveData<Movie>
        get() = _movieLD

    private val _errorLD = MutableLiveData<String>()
    val errorLD: LiveData<String>
        get() = _errorLD

    private val _trailersLD = MutableLiveData<List<Trailer>>()
    val trailerLD: LiveData<List<Trailer>>
        get() = _trailersLD

    init {
        viewModelScope.launch {
            val detailsResult = getMovieDetailsUc(movieId)
            when (detailsResult) {
                is Success<Movie> -> _movieLD.value = detailsResult.data
                is Error -> _errorLD.value = detailsResult.exception.message.toString()
            }

            val trailersResult = getTrailersUc(movieId)
            when (trailersResult) {
                is Success<List<Trailer>> -> _trailersLD.value = trailersResult.data
                is Error -> _errorLD.value = trailersResult.exception.message.toString()
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(movieId: Int): MovieDetailsViewModel
    }
}