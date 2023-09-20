package com.ubcompany.umbba_android.data.repository

import com.ubcompany.umbba_android.data.datasource.LoginRemoteDataSource
import com.ubcompany.umbba_android.data.model.request.LoginRequestDto
import com.ubcompany.umbba_android.data.model.response.LoginResponseDto
import com.ubcompany.umbba_android.domain.repository.LoginRepository
import timber.log.Timber
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginRemoteDataSource: LoginRemoteDataSource
) : LoginRepository {
    override suspend fun postLogin(loginRequestDto: LoginRequestDto): Result<LoginResponseDto> =
        runCatching {
            loginRemoteDataSource.postLogin(loginRequestDto)
        }.onSuccess {
            Timber.d("Impl login 성공")
        }.onFailure {
            Timber.e("Impl login 실패")
        }
}