package com.sopt.umbba_android.data.repository

import android.util.Log
import com.sopt.umbba_android.data.datasource.SettingRemoteDataSource
import com.sopt.umbba_android.data.model.response.LogOutResponseDto
import com.sopt.umbba_android.data.model.response.SignOutResponseDto
import com.sopt.umbba_android.domain.repository.SettingRepository

class SettingRepositoryImpl(private val settingRemoteDataSource: SettingRemoteDataSource) :
    SettingRepository {
    override suspend fun logout(): Result<LogOutResponseDto> =
        runCatching {
            settingRemoteDataSource.logout()
        }.onSuccess {
            Log.e("hyeon","Impl 로그아웃 성공")
        }.onFailure {
            Log.e("hyeon","Impl 로그아웃 실패")
        }

    override suspend fun signout(): Result<SignOutResponseDto> =
        runCatching {
            settingRemoteDataSource.signout()
        }.onSuccess {
            Log.e("hyeon","Impl 회원탈퇴 성공")
        }.onFailure {
            Log.e("hyeon","Impl 회원탈퇴 실패")
        }
}