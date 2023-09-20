package com.ubcompany.umbba_android.data.datasource

import com.ubcompany.umbba_android.data.model.request.InviteCodeRequestDto
import com.ubcompany.umbba_android.data.model.request.ReceiveInfoRequestDto
import com.ubcompany.umbba_android.data.model.request.SendInfoRequestDto
import com.ubcompany.umbba_android.data.model.response.InviteCodeResponseDto
import com.ubcompany.umbba_android.data.model.response.ReceiveInfoResponseDto
import com.ubcompany.umbba_android.data.model.response.SendInfoResponseDto
import com.ubcompany.umbba_android.data.service.OnboardingService
import javax.inject.Inject

class OnboardingRemoteDataSource @Inject constructor(
    private val onboardingService: OnboardingService
) {
    suspend fun setFamily(onboardingRequestDto: InviteCodeRequestDto): InviteCodeResponseDto =
        onboardingService.setFamily(onboardingRequestDto)

    suspend fun setSendInfo(sendInfoRequestDto: SendInfoRequestDto): SendInfoResponseDto =
        onboardingService.setSendInfo(sendInfoRequestDto)

    suspend fun setReceiveInfo(receiveInfoRequestDto: ReceiveInfoRequestDto): ReceiveInfoResponseDto =
        onboardingService.setReceiveInfo(receiveInfoRequestDto)
}