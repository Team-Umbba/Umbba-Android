package com.ubcompany.umbba_android.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.ubcompany.umbba_android.BuildConfig
import com.ubcompany.umbba_android.presentation.login.LoginActivity.Companion.DID_USER_CLEAR_INVITE_CODE
import com.ubcompany.umbba_android.presentation.login.LoginActivity.Companion.DID_USER_CLEAR_ONBOARD

const val FILE_NAME = "UMBBA"

object SharedPreferences {
    private lateinit var preferences: SharedPreferences

    fun init(context: Context) {
        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        preferences =
            if (BuildConfig.DEBUG) {
                context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
            } else {
                EncryptedSharedPreferences.create(
                    FILE_NAME,
                    masterKeyAlias,
                    context,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                )
            }
    }

    fun setKakaoString(key: String, value: String?) {
        preferences.edit { putString(key, value) }
    }

    fun getKakaoString(key: String): String? {
        return preferences.getString(key, null)
    }

    fun setString(key: String, value: String?) {
        preferences.edit { putString(key, value) }
    }

    fun getString(key: String): String? {
        return preferences.getString(key, null)
    }

    fun setOnboardingBoolean(key: String, value: Boolean) {
        preferences.edit { putBoolean(key, value) }
    }

    fun getOnboardingBoolean(key: String): Boolean {
        return preferences.getBoolean(key, false)
    }

    fun setInviteCodeBoolean(key: String, value: Boolean) {
        preferences.edit { putBoolean(key, value) }
    }

    fun getInviteCodeBoolean(key: String): Boolean {
        return preferences.getBoolean(key, false)
    }

    fun setUpdateAvailableBoolean(key: String, value: Boolean) {
        preferences.edit { putBoolean(key, value) }
    }

    fun getUpdateAvailableBoolean(key: String): Boolean {
        return preferences.getBoolean(key, false)
    }

    fun setTutorialBoolean(key: String, value: Boolean) {
        preferences.edit { putBoolean(key, value) }
    }

    fun getTutorialBoolean(key: String) : Boolean {
        return preferences.getBoolean(key,false)
    }

    private fun clear() {
        preferences.edit { clear() }
    }

    fun clearForLogout() {
        clear()
        setOnboardingBoolean(DID_USER_CLEAR_ONBOARD, true)
        setInviteCodeBoolean(DID_USER_CLEAR_INVITE_CODE, true)
    }

    fun clearForSignout() {
        clear()
    }
}