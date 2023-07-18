package com.sopt.umbba_android.data.repository

import android.util.Log
import com.sopt.umbba_android.data.datasource.LoginRemoteDataSource
import com.sopt.umbba_android.data.model.request.LoginRequestDto
import com.sopt.umbba_android.data.model.response.LoginResponseDto
import com.sopt.umbba_android.domain.repository.LoginRepository
import timber.log.Timber

class LoginRepositoryImpl(
    private val loginRemoteDataSource: LoginRemoteDataSource
) : LoginRepository {
    override suspend fun postLogin(loginRequestDto: LoginRequestDto): Result<LoginResponseDto> =
        runCatching {
            loginRemoteDataSource.postLogin(loginRequestDto)
        }.onSuccess {
            Log.e("yeonjin", "Impl login 성공")
        }.onFailure {
            Log.e("yeonjin", "Impl login 실패")
        }
}