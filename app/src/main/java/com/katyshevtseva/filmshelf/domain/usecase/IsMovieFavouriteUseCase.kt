package com.katyshevtseva.filmshelf.domain.usecase

import com.katyshevtseva.filmshelf.domain.repository.LocalRepository
import javax.inject.Inject

class IsMovieFavouriteUseCase @Inject constructor(
    private val localRepository: LocalRepository
) {

    suspend operator fun invoke(kpId: Int): Boolean {
        return localRepository.isMovieFavourite(kpId)
    }
}