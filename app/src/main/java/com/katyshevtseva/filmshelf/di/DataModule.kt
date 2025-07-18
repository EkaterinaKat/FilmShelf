package com.katyshevtseva.filmshelf.di

import android.app.Application
import com.katyshevtseva.filmshelf.data.local.MovieDao
import com.katyshevtseva.filmshelf.data.local.MovieDatabase
import com.katyshevtseva.filmshelf.data.remote.ApiFactory
import com.katyshevtseva.filmshelf.data.remote.ApiService
import com.katyshevtseva.filmshelf.data.repository.FiltersValuesRepositoryImpl
import com.katyshevtseva.filmshelf.data.repository.MovieRepositoryImpl
import com.katyshevtseva.filmshelf.domain.repository.FiltersValuesRepository
import com.katyshevtseva.filmshelf.domain.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @Binds
    @ApplicationScope
    fun bindMovieRepository(impl: MovieRepositoryImpl): MovieRepository

    @Binds
    @ApplicationScope
    fun bindFiltersRepository(impl: FiltersValuesRepositoryImpl): FiltersValuesRepository

    companion object {

        @Provides
        @ApplicationScope
        fun provideApiService(apiFactory: ApiFactory): ApiService {
            return apiFactory.apiService
        }

        @Provides
        @ApplicationScope
        fun provideMovieDao(application: Application): MovieDao {
            return MovieDatabase.getInstance(application).movieDao()
        }
    }
}