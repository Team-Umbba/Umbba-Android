package com.sopt.umbba_android.data.datasource

import com.sopt.umbba_android.data.model.ServicePool

class SettingRemoteDataSource {
    private val settingService = ServicePool.settingService

    suspend fun logout() = settingService.logout()

    suspend fun deleteAccount() = settingService.deleteAccount()
}