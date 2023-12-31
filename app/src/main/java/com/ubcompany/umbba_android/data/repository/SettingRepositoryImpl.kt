package com.ubcompany.umbba_android.data.repository

import com.ubcompany.umbba_android.data.datasource.SettingRemoteDataSource
import com.ubcompany.umbba_android.data.model.response.LogOutResponseDto
import com.ubcompany.umbba_android.data.model.response.SignOutResponseDto
import com.ubcompany.umbba_android.domain.repository.SettingRepository
import timber.log.Timber
import javax.inject.Inject

class SettingRepositoryImpl @Inject constructor(private val settingRemoteDataSource: SettingRemoteDataSource) :
    SettingRepository {
    override suspend fun logout(): Result<LogOutResponseDto> =
        runCatching {
            settingRemoteDataSource.logout()
        }.onSuccess {
            Timber.d("Impl 로그아웃 성공")
        }.onFailure {
            Timber.e("Impl 로그아웃 실패")
        }

    override suspend fun signout(): Result<SignOutResponseDto> =
        runCatching {
            settingRemoteDataSource.signout()
        }.onSuccess {
            Timber.d("Impl 회원탈퇴 성공")
        }.onFailure {
            Timber.e("Impl 회원탈퇴 실패")
        }
}