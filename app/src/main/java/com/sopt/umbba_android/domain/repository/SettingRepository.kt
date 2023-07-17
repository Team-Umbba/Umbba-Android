package com.sopt.umbba_android.domain.repository

import com.sopt.umbba_android.data.model.response.LogOutResponseDto

interface SettingRepository {
    suspend fun logout():Result<LogOutResponseDto>
    suspend fun deleteAccount()
}