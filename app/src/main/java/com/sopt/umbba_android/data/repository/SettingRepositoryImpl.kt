package com.sopt.umbba_android.data.repository

import com.sopt.umbba_android.data.datasource.SettingRemoteDataSource
import com.sopt.umbba_android.data.model.response.LogOutResponseDto
import com.sopt.umbba_android.domain.repository.SettingRepository
import timber.log.Timber

class SettingRepositoryImpl(private val settingRemoteDataSource: SettingRemoteDataSource) :
    SettingRepository {
    override suspend fun logout():Result<LogOutResponseDto> =
        runCatching {
            settingRemoteDataSource.logout()
        }.onSuccess {
            Timber.e("로그아웃 성공")
        }.onFailure {
            Timber.e("로그아웃 실패")
        }

    override suspend fun deleteAccount() {
        TODO("Not yet implemented")
    }
}