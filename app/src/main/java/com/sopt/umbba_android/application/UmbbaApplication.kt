package com.sopt.umbba_android.application

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.kakao.sdk.common.KakaoSdk
import com.sopt.umbba_android.BuildConfig.KAKAO_NATIVE_APP_KEY
import com.sopt.umbba_android.data.local.SharedPreferences

class UmbbaApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        initKakao()
        setupSharedPreferences()
    }

    private fun initKakao() {
        KakaoSdk.init(this, KAKAO_NATIVE_APP_KEY)
    }

    private fun setupSharedPreferences() {
        SharedPreferences.init(this)
    }
}