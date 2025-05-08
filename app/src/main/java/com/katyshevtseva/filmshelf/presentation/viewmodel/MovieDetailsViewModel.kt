package com.katyshevtseva.filmshelf.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.katyshevtseva.filmshelf.domain.model.Movie
import com.katyshevtseva.filmshelf.domain.result.Error
import com.katyshevtseva.filmshelf.domain.result.Success
import com.katyshevtseva.filmshelf.domain.usecase.GetMovieDetailsUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class MovieDetailsViewModel @AssistedInject constructor(
    @Assisted private val movieId: Int,
    private val useCAse: GetMovieDetailsUseCase
) : ViewModel() {

    private val _movieLD = MutableLiveData<Movie>()
    val movieLD: LiveData<Movie>
        get() = _movieLD

    private val _errorLD = MutableLiveData<String>()
    val errorLD: LiveData<String>
        get() = _errorLD


    init {
        viewModelScope.launch {
            val result = useCAse.invoke(movieId)
            when (result) {
                is Success<Movie> -> _movieLD.value = result.data
                is Error -> _errorLD.value = result.exception.message.toString()
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(movieId: Int): MovieDetailsViewModel
    }
}