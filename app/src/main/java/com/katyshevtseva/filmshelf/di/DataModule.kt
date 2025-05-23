package com.katyshevtseva.filmshelf.di

import android.app.Application
import com.katyshevtseva.filmshelf.data.local.MovieDao
import com.katyshevtseva.filmshelf.data.local.MovieDatabase
import com.katyshevtseva.filmshelf.data.remote.ApiFactory
import com.katyshevtseva.filmshelf.data.remote.ApiService
import com.katyshevtseva.filmshelf.data.repository.MovieRepositoryImpl
import com.katyshevtseva.filmshelf.domain.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @Binds
    @ApplicationScope
    fun bindMovieRepository(impl: MovieRepositoryImpl): MovieRepository

    companion object {

        @Provides
        @ApplicationScope
        fun provideApiService(): ApiService {
            return ApiFactory.apiService
        }

        @Provides
        @ApplicationScope
        fun provideMovieDao(application: Application): MovieDao {
            return MovieDatabase.getInstance(application).movieDao()
        }
    }
}