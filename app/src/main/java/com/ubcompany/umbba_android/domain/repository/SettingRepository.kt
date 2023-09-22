package com.ubcompany.umbba_android.domain.repository

import com.ubcompany.umbba_android.data.model.response.LogOutResponseDto
import com.ubcompany.umbba_android.data.model.response.SignOutResponseDto

interface SettingRepository {
    suspend fun logout(): Result<LogOutResponseDto>
    suspend fun signout(): Result<SignOutResponseDto>
}