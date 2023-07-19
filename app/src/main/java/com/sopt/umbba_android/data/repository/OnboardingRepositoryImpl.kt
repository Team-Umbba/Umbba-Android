package com.sopt.umbba_android.data.repository

import android.util.Log
import com.sopt.umbba_android.data.datasource.OnboardingRemoteDataSource
import com.sopt.umbba_android.data.model.request.InviteCodeRequestDto
import com.sopt.umbba_android.data.model.request.ReceiveInfoRequestDto
import com.sopt.umbba_android.data.model.request.SendInfoRequestDto
import com.sopt.umbba_android.data.model.response.InviteCodeResponseDto
import com.sopt.umbba_android.data.model.response.ReceiveInfoResponseDto
import com.sopt.umbba_android.data.model.response.SendInfoResponseDto
import com.sopt.umbba_android.domain.repository.OnboardingRepository

class OnboardingRepositoryImpl(
    private val onboardingRemoteDataSource: OnboardingRemoteDataSource
) : OnboardingRepository {
    override suspend fun setFamily(inviteCodeRequestDto: InviteCodeRequestDto): Result<InviteCodeResponseDto> =
        runCatching {
            onboardingRemoteDataSource.setFamily(inviteCodeRequestDto)
        }.onSuccess {
            Log.e("yeonjin", "onboarding setFamily Impl 성공")
        }.onFailure {
            Log.e("yeonjin", "onboarding setFamily Impl 실패")
        }

    override suspend fun setSendInfo(sendInfoRequestDto: SendInfoRequestDto): Result<SendInfoResponseDto> =
        runCatching {
            onboardingRemoteDataSource.setSendInfo(sendInfoRequestDto)
        }.onSuccess {
            Log.e("yeonjin", "onboarding setSendInfo Impl 성공")
        }.onFailure {
            Log.e("yeonjin", "onboarding setSendInfo Impl 성공")
        }

    override suspend fun setReceiveInfo(receiveInfoRequestDto: ReceiveInfoRequestDto): Result<ReceiveInfoResponseDto> =
        runCatching {
            onboardingRemoteDataSource.setReceiveInfo(receiveInfoRequestDto)
        }.onSuccess {
            Log.e("yeonjin", "onboarding setReceiveInfo Impl 성공")
        }.onFailure {
            Log.e("yeonjin", "onboarding setReceiveInfo Impl 성공")
        }
}