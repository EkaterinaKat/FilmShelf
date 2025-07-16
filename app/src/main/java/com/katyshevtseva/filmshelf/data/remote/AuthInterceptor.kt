package com.katyshevtseva.filmshelf.data.remote

import jakarta.inject.Inject
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor @Inject constructor(
    private val tokenStorage: TokenStorage
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val token = tokenStorage.getToken()

        val newRequest = originalRequest.newBuilder()
            .apply {
                token?.let {
                    header("X-API-KEY", it)
                }
            }
            .build()

        return chain.proceed(newRequest)
    }
}