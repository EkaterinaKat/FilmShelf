package com.katyshevtseva.filmshelf.di

import android.app.Application
import com.katyshevtseva.filmshelf.presentation.FavoritesFragment
import com.katyshevtseva.filmshelf.presentation.FiltersActivity
import com.katyshevtseva.filmshelf.presentation.HomeFragment
import com.katyshevtseva.filmshelf.presentation.MovieDetailsActivity
import com.katyshevtseva.filmshelf.presentation.SearchFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        ViewModelModule::class,
        DataModule::class
    ]
)
interface ApplicationComponent {

    fun inject(fragment: HomeFragment)

    fun inject(fragment: FavoritesFragment)

    fun inject(fragment: SearchFragment)

    fun inject(activity: MovieDetailsActivity)

    fun inject(activity: FiltersActivity)

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance application: Application,
            @BindsInstance token: String
        ): ApplicationComponent
    }
}