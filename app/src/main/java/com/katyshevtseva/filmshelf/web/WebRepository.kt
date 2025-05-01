package com.katyshevtseva.filmshelf.web

import android.util.Log
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class WebRepository {

    fun test() {

        val dis = ApiFactory.apiService.loadMovies1(
            1,
            "2BW30XT-0E84FXT-PC1P59Z-1BW2MWB",
            "votes.kp",
            "-1",
            "3-10",
            "20"
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { movieResponse ->
                    Log.i("tag67841", movieResponse.toString())
                },
                { throwable ->
                    Log.i("tag67841", throwable.toString() + "\n" + throwable.message)
                })

    }

}