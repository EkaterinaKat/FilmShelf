package com.katyshevtseva.filmshelf.domain.result

sealed class Result<out T>

class Success<T>(val data: T) : Result<T>()
class Error(val exception: Exception) : Result<Nothing>()
