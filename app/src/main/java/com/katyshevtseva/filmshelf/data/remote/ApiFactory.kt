package com.katyshevtseva.filmshelf.data.remote

import com.katyshevtseva.filmshelf.di.ApplicationScope
import jakarta.inject.Inject
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@ApplicationScope
class ApiFactory @Inject constructor(
    authInterceptor: AuthInterceptor
) {

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)

    companion object {
        private const val BASE_URL = "https://api.kinopoisk.dev/"
    }
}