package com.katyshevtseva.filmshelf

import android.app.Application
import com.katyshevtseva.filmshelf.di.DaggerApplicationComponent

class FilmShelfApp : Application() {

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}