package com.ubcompany.umbba_android.data.datasource

import com.ubcompany.umbba_android.data.service.SettingService
import javax.inject.Inject

class SettingRemoteDataSource @Inject constructor(
    private val settingService : SettingService
) {
    suspend fun logout() = settingService.logout()

    suspend fun signout() = settingService.signout()
}