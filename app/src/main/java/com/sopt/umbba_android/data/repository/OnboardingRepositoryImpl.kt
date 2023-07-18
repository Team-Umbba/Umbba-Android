package com.sopt.umbba_android.data.repository

import android.util.Log
import com.sopt.umbba_android.data.datasource.OnboardingRemoteDataSource
import com.sopt.umbba_android.data.model.request.InviteCodeRequestDto
import com.sopt.umbba_android.data.model.response.InviteCodeResponseDto
import com.sopt.umbba_android.domain.repository.OnboardingRepository

class OnboardingRepositoryImpl(
    private val onboardingRemoteDataSource: OnboardingRemoteDataSource
) : OnboardingRepository {
    override suspend fun setFamily(inviteCodeRequestDto: InviteCodeRequestDto): Result<InviteCodeResponseDto> =
        runCatching {
            onboardingRemoteDataSource.setFamily(inviteCodeRequestDto)
        }.onSuccess {
            Log.e("yeonjin", "onboarding Impl 성공")
        }.onFailure {
            Log.e("yeonjin", "onboarding Impl 실패")
        }
}