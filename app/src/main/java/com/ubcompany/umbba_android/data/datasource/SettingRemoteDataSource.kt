package com.ubcompany.umbba_android.data.datasource

import com.ubcompany.umbba_android.data.model.ServicePool

class SettingRemoteDataSource {
    private val settingService = ServicePool.settingService

    suspend fun logout() = settingService.logout()

    suspend fun signout() = settingService.signout()
}