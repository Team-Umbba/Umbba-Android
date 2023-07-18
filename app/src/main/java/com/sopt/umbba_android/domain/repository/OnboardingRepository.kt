package com.sopt.umbba_android.domain.repository

import com.sopt.umbba_android.data.model.request.InviteCodeRequestDto
import com.sopt.umbba_android.data.model.response.InviteCodeResponseDto

interface OnboardingRepository {
    suspend fun setFamily(inviteCodeRequestDto: InviteCodeRequestDto): Result<InviteCodeResponseDto>
}