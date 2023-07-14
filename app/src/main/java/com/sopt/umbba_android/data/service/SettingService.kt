package com.sopt.umbba_android.data.service

interface SettingService {
    suspend fun logout()
    suspend fun deleteAccount()
}