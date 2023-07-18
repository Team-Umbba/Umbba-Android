package com.sopt.umbba_android.data.service

import com.sopt.umbba_android.data.model.request.InviteCodeRequestDto
import com.sopt.umbba_android.data.model.response.InviteCodeResponseDto
import retrofit2.http.Body
import retrofit2.http.PATCH

interface OnboardingService {
    @PATCH("/onboard/match")
    suspend fun setFamily(
        @Body body: InviteCodeRequestDto
    ): InviteCodeResponseDto
}