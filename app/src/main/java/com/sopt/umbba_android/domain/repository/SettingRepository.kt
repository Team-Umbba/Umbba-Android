package com.sopt.umbba_android.domain.repository

interface SettingRepository {
    suspend fun logout()
    suspend fun deleteAccount()
}