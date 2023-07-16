package com.sopt.umbba_android

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

class UmbbaApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}