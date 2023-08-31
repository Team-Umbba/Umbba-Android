package com.ubcompany.umbba_android.data.service

import com.ubcompany.umbba_android.data.model.request.InviteCodeRequestDto
import com.ubcompany.umbba_android.data.model.request.ReceiveInfoRequestDto
import com.ubcompany.umbba_android.data.model.request.SendInfoRequestDto
import com.ubcompany.umbba_android.data.model.response.InviteCodeResponseDto
import com.ubcompany.umbba_android.data.model.response.ReceiveInfoResponseDto
import com.ubcompany.umbba_android.data.model.response.SendInfoResponseDto
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.POST

interface OnboardingService {
    @PATCH("/onboard/match")
    suspend fun setFamily(
        @Body body: InviteCodeRequestDto
    ): InviteCodeResponseDto

    @POST("/onboard/invite")
    suspend fun setSendInfo(
        @Body body: SendInfoRequestDto
    ): SendInfoResponseDto

    @PATCH("/onboard/receive")
    suspend fun setReceiveInfo(
        @Body body: ReceiveInfoRequestDto
    ): ReceiveInfoResponseDto
}