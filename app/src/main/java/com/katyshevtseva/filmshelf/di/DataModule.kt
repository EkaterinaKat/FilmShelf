package com.katyshevtseva.filmshelf.di

import com.katyshevtseva.filmshelf.data.remote.ApiFactory
import com.katyshevtseva.filmshelf.data.remote.ApiService
import com.katyshevtseva.filmshelf.data.repository.LocalRepositoryImpl
import com.katyshevtseva.filmshelf.data.repository.RemoteRepositoryImpl
import com.katyshevtseva.filmshelf.domain.repository.LocalRepository
import com.katyshevtseva.filmshelf.domain.repository.RemoteRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @Binds
    @ApplicationScope
    fun bindRemoteRepository(impl: RemoteRepositoryImpl): RemoteRepository

    @Binds
    @ApplicationScope
    fun bindLocalRepository(impl: LocalRepositoryImpl): LocalRepository

    companion object {

        @Provides
        @ApplicationScope
        fun provideApiService(): ApiService {
            return ApiFactory.apiService
        }
    }
}