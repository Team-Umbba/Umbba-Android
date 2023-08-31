package com.sopt.umbba_android.domain.repository

import com.sopt.umbba_android.data.model.request.LoginRequestDto
import com.sopt.umbba_android.data.model.response.LoginResponseDto

interface LoginRepository {
    suspend fun postLogin(loginRequestDto: LoginRequestDto): Result<LoginResponseDto>
}