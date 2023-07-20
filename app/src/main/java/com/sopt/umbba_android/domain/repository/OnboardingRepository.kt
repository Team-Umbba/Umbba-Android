package com.sopt.umbba_android.domain.repository

import com.sopt.umbba_android.data.model.request.InviteCodeRequestDto
import com.sopt.umbba_android.data.model.request.ReceiveInfoRequestDto
import com.sopt.umbba_android.data.model.request.SendInfoRequestDto
import com.sopt.umbba_android.data.model.response.InviteCodeResponseDto
import com.sopt.umbba_android.data.model.response.ReceiveInfoResponseDto
import com.sopt.umbba_android.data.model.response.SendInfoResponseDto

interface OnboardingRepository {
    suspend fun setFamily(inviteCodeRequestDto: InviteCodeRequestDto): Result<InviteCodeResponseDto>

    suspend fun setSendInfo(sendInfoRequestDto: SendInfoRequestDto): Result<SendInfoResponseDto>

    suspend fun setReceiveInfo(receiveInfoRequestDto: ReceiveInfoRequestDto): Result<ReceiveInfoResponseDto>
}