package com.sopt.umbba_android.application

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.kakao.sdk.common.KakaoSdk
import com.sopt.umbba_android.BuildConfig.KAKAO_NATIVE_APP_KEY

class UmbbaApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, KAKAO_NATIVE_APP_KEY)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}