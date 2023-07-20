package com.sopt.umbba_android.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.sopt.umbba_android.BuildConfig
import com.sopt.umbba_android.presentation.login.LoginActivity.Companion.DID_USER_CLEAR_INVITE_CODE
import com.sopt.umbba_android.presentation.login.LoginActivity.Companion.DID_USER_CLEAR_ONBOARD

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

    //토큰 저장
    fun setString(key: String, value: String?) {
        preferences.edit { putString(key, value) }
    }
    //토큰 가져오기 - 자동 로그인 확인
    fun getString(key: String): String? {
        return preferences.getString(key, null)
    }
    //온보딩 완료 상태 저장
    fun setOnboardingBoolean(key: String, value: Boolean) {
        preferences.edit { putBoolean(key, value) }
    }
    //온보딩 완료했는지 가져오기 - 온보딩 완료 확인
    fun getOnboardingBoolean(key: String): Boolean {
        return preferences.getBoolean(key, false)
    }

    fun setInviteCodeBoolean(key: String, value: Boolean) {
        preferences.edit { putBoolean(key, value) }
    }

    fun getInviteCodeBoolean(key: String): Boolean {
        return preferences.getBoolean(key, false)
    }

    private fun clear() {
        preferences.edit { clear() }
    }

    fun clearForLogout() {
        clear()
        setOnboardingBoolean(DID_USER_CLEAR_ONBOARD, true)
        setInviteCodeBoolean(DID_USER_CLEAR_INVITE_CODE, true)
    }

    fun clearForSignout(){
        clear()
    }
}