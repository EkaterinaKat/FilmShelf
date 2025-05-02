package com.katyshevtseva.filmshelf.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.katyshevtseva.filmshelf.domain.usecase.GetBestMoviesUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getBestMoviesUseCase: GetBestMoviesUseCase
) : ViewModel() {

    private val _testLD = MutableLiveData<String>()
    val testLD: LiveData<String>
        get() = _testLD

    init {
        viewModelScope.launch {
            _testLD.value = getBestMoviesUseCase.invoke().toString()
        }
    }
}