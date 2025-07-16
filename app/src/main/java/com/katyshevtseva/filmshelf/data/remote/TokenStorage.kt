package com.katyshevtseva.filmshelf.data.remote

import android.app.Application
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import jakarta.inject.Inject

class TokenStorage @Inject constructor(
    application: Application,
    token: String
) {

    private val prefs: SharedPreferences = EncryptedSharedPreferences.create(
        "secure_prefs",
        MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
        application,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    init {
        if (getToken() == EMPTY_TOKEN) {
            setToken(token)
        }
    }

    fun getToken(): String? = prefs.getString("auth_token", EMPTY_TOKEN)

    private fun setToken(token: String) = prefs.edit { putString("auth_token", token) }

    companion object {
        private const val EMPTY_TOKEN = "-1"
    }
}