package com.katyshevtseva.filmshelf.di

import androidx.lifecycle.ViewModel
import com.katyshevtseva.filmshelf.presentation.viewmodel.FavoritesViewModel
import com.katyshevtseva.filmshelf.presentation.viewmodel.FiltersViewModel
import com.katyshevtseva.filmshelf.presentation.viewmodel.HomeViewModel
import com.katyshevtseva.filmshelf.presentation.viewmodel.SearchViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FavoritesViewModel::class)
    fun bindFavoritesViewModel(viewModel: FavoritesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    fun bindSearchViewModel(viewModel: SearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FiltersViewModel::class)
    fun bindFiltersViewModel(viewModel: FiltersViewModel): ViewModel
}
