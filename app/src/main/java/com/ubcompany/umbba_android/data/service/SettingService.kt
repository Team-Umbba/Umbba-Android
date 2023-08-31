package com.ubcompany.umbba_android.data.service

import com.ubcompany.umbba_android.data.model.response.LogOutResponseDto
import com.ubcompany.umbba_android.data.model.response.SignOutResponseDto
import retrofit2.http.PATCH

interface SettingService {
    @PATCH("/log-out")
    suspend fun logout(): LogOutResponseDto

    @PATCH("/sign-out")
    suspend fun signout(): SignOutResponseDto
}