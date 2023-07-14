package com.sopt.umbba_android.data.repository

import com.sopt.umbba_android.data.datasource.SettingRemoteDataSource
import com.sopt.umbba_android.domain.repository.SettingRepository

class SettingRepositoryImpl(private val settingRemoteDataSource: SettingRemoteDataSource) :
    SettingRepository {
    override suspend fun logout() {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAccount() {
        TODO("Not yet implemented")
    }
}