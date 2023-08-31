package com.ubcompany.umbba_android.application

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.kakao.sdk.common.KakaoSdk
import com.ubcompany.umbba_android.BuildConfig
import com.ubcompany.umbba_android.BuildConfig.KAKAO_NATIVE_APP_KEY
import com.ubcompany.umbba_android.data.local.SharedPreferences
import timber.log.Timber

class UmbbaApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKakao()
        setupSharedPreferences()
        stopDarkMode()
        useTimber()
    }

    private fun initKakao() {
        KakaoSdk.init(this, KAKAO_NATIVE_APP_KEY)
    }

    private fun setupSharedPreferences() {
        SharedPreferences.init(this)
    }

    private fun stopDarkMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    private fun useTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}