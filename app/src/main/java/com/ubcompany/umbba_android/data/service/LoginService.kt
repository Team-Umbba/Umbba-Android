package com.ubcompany.umbba_android.data.service

import com.ubcompany.umbba_android.data.model.request.LoginRequestDto
import com.ubcompany.umbba_android.data.model.response.LoginResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {

    @POST("/login")
    suspend fun postLogin(
        @Body body: LoginRequestDto
    ): LoginResponseDto
}