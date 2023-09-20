package com.ubcompany.umbba_android.data.datasource

import com.ubcompany.umbba_android.data.model.ServicePool
import com.ubcompany.umbba_android.data.model.request.LoginRequestDto
import com.ubcompany.umbba_android.data.model.response.LoginResponseDto
import com.ubcompany.umbba_android.data.service.LoginService
import javax.inject.Inject

class LoginRemoteDataSource  @Inject constructor(
    private val loginService : LoginService
) {
    suspend fun postLogin(loginRequestDto: LoginRequestDto): LoginResponseDto =
        loginService.postLogin(loginRequestDto)
}