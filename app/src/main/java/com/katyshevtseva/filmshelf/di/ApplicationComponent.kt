package com.katyshevtseva.filmshelf.di

import android.app.Application
import com.katyshevtseva.filmshelf.presentation.FavoritesFragment
import com.katyshevtseva.filmshelf.presentation.HomeFragment
import com.katyshevtseva.filmshelf.presentation.MovieDetailsActivity
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

    fun inject(activity: MovieDetailsActivity)

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}