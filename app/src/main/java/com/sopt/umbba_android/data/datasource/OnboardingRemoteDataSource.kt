package com.sopt.umbba_android.data.datasource

import com.sopt.umbba_android.data.model.ServicePool
import com.sopt.umbba_android.data.model.request.InviteCodeRequestDto
import com.sopt.umbba_android.data.model.response.InviteCodeResponseDto

class OnboardingRemoteDataSource {
    private val onboardingService = ServicePool.onboardingService
    suspend fun setFamily(onboardingRequestDto: InviteCodeRequestDto) : InviteCodeResponseDto =
        onboardingService.setFamily(onboardingRequestDto)
}