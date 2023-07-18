package com.sopt.umbba_android.data.datasource

import com.sopt.umbba_android.data.model.ServicePool
import com.sopt.umbba_android.data.model.request.LoginRequestDto
import com.sopt.umbba_android.data.model.response.LoginResponseDto

class LoginRemoteDataSource {
    private val loginService = ServicePool.loginService
    suspend fun postLogin(loginRequestDto: LoginRequestDto) : LoginResponseDto =
        loginService.postLogin(loginRequestDto)
}