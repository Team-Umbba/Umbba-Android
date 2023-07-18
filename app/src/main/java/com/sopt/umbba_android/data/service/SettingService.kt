package com.sopt.umbba_android.data.service

import com.sopt.umbba_android.data.model.response.LogOutResponseDto
import retrofit2.http.PATCH

interface SettingService {
    @PATCH("/log-out")
    suspend fun logout(): LogOutResponseDto
    suspend fun deleteAccount()
}