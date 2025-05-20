package com.katyshevtseva.filmshelf.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.katyshevtseva.filmshelf.domain.model.Movie
import com.katyshevtseva.filmshelf.domain.model.Trailer
import com.katyshevtseva.filmshelf.domain.result.Error
import com.katyshevtseva.filmshelf.domain.result.Success
import com.katyshevtseva.filmshelf.domain.usecase.DeleteFromFavouriteMoviesUseCase
import com.katyshevtseva.filmshelf.domain.usecase.GetMovieDetailsUseCase
import com.katyshevtseva.filmshelf.domain.usecase.GetTrailersUseCase
import com.katyshevtseva.filmshelf.domain.usecase.IsMovieFavouriteUseCase
import com.katyshevtseva.filmshelf.domain.usecase.SaveFavouriteMovieUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class MovieDetailsViewModel @AssistedInject constructor(
    @Assisted private val movieKpId: Int,
    private val getMovieDetailsUc: GetMovieDetailsUseCase,
    private val getTrailersUc: GetTrailersUseCase,
    private val isMovieFavouriteUseCase: IsMovieFavouriteUseCase,
    private val saveFavouriteMovieUseCase: SaveFavouriteMovieUseCase,
    private val deleteFromFavouriteMoviesUseCase: DeleteFromFavouriteMoviesUseCase
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

    private val _isFavouriteLD = MutableLiveData<Boolean>()
    val isFavouriteLD: LiveData<Boolean>
        get() = _isFavouriteLD

    private val _loadingLD = MutableLiveData<Boolean>()
    val loadingLD: LiveData<Boolean>
        get() = _loadingLD

    init {
        viewModelScope.launch {
            _loadingLD.value = true
            loadDetails()
            loadTrailers()
            setIsFavourite()
            _loadingLD.value = false
        }
    }

    fun onFavouriteClick() {
        if (loadingLD.value == true) {
            return
        }
        viewModelScope.launch {
            _loadingLD.value = true
            if (requireNotNull(isFavouriteLD.value) { "isFavourite must not be null" }) {
                deleteFromFavouriteMoviesUseCase.invoke(movieKpId)
            } else {
                saveFavouriteMovieUseCase.invoke(
                    requireNotNull(_movieLD.value) { "movie must not be null" }
                )
            }
            setIsFavourite()
            _loadingLD.value = false
        }
    }

    private suspend fun loadDetails() {
        val detailsResult = getMovieDetailsUc(movieKpId)
        when (detailsResult) {
            is Success<Movie> -> _movieLD.value = detailsResult.data
            is Error -> _errorLD.value = detailsResult.exception.message.toString()
        }
    }

    private suspend fun loadTrailers() {
        val trailersResult = getTrailersUc(movieKpId)
        when (trailersResult) {
            is Success<List<Trailer>> -> _trailersLD.value = trailersResult.data
            is Error -> _errorLD.value = trailersResult.exception.message.toString()
        }
    }

    private suspend fun setIsFavourite() {
        _isFavouriteLD.value = isMovieFavouriteUseCase.invoke(movieKpId)
    }

    @AssistedFactory
    interface Factory {
        fun create(movieKpId: Int): MovieDetailsViewModel
    }
}