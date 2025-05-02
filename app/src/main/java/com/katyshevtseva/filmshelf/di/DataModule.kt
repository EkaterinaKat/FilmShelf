package com.katyshevtseva.filmshelf.di

import com.katyshevtseva.filmshelf.data.remote.ApiFactory
import com.katyshevtseva.filmshelf.data.remote.ApiService
import com.katyshevtseva.filmshelf.data.repository.MoviesRepositoryImpl
import com.katyshevtseva.filmshelf.domain.repository.MoviesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @Binds
    @ApplicationScope
    fun bindMoviesRepository(impl: MoviesRepositoryImpl): MoviesRepository

    companion object {

        @Provides
        @ApplicationScope
        fun provideApiService(): ApiService {
            return ApiFactory.apiService
        }
    }
}