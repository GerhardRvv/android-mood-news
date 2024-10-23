package com.grv.common.util

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.grv.core_common.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor(
    @ApplicationContext val context: Context
) {

    companion object {
        private const val BASE_URL = "base_url"
        private const val AUTH_TOKEN = "auth_token"
    }

    private val masterKey = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
    private var preferences = EncryptedSharedPreferences.create(
        context.getString(R.string.app_name),
        masterKey,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun setBaseUrl(baseUrl: String) {
        val editor = preferences.edit()
        editor.putString(BASE_URL, baseUrl)
        editor.apply()
    }

    fun getBaseUrl(): String? {
        return preferences.getString(BASE_URL, null)
    }

    fun setAuthToken(token: String) {
        val editor = preferences.edit()
        editor.putString(AUTH_TOKEN, token)
        editor.apply()
    }

    fun getAuthToken(): String? {
        return preferences.getString(AUTH_TOKEN, null)
    }
}
