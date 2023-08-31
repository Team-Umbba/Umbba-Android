package com.ubcompany.umbba_android.data.repository

import com.ubcompany.umbba_android.data.datasource.OnboardingRemoteDataSource
import com.ubcompany.umbba_android.data.model.request.InviteCodeRequestDto
import com.ubcompany.umbba_android.data.model.request.ReceiveInfoRequestDto
import com.ubcompany.umbba_android.data.model.request.SendInfoRequestDto
import com.ubcompany.umbba_android.data.model.response.InviteCodeResponseDto
import com.ubcompany.umbba_android.data.model.response.ReceiveInfoResponseDto
import com.ubcompany.umbba_android.data.model.response.SendInfoResponseDto
import com.ubcompany.umbba_android.domain.repository.OnboardingRepository
import timber.log.Timber

class OnboardingRepositoryImpl(
    private val onboardingRemoteDataSource: OnboardingRemoteDataSource
) : OnboardingRepository {
    override suspend fun setFamily(inviteCodeRequestDto: InviteCodeRequestDto): Result<InviteCodeResponseDto> =
        runCatching {
            onboardingRemoteDataSource.setFamily(inviteCodeRequestDto)
        }.onSuccess {
            Timber.d("onboarding setFamily Impl 성공")
        }.onFailure {
            Timber.e("onboarding setFamily Impl 실패")
        }

    override suspend fun setSendInfo(sendInfoRequestDto: SendInfoRequestDto): Result<SendInfoResponseDto> =
        runCatching {
            onboardingRemoteDataSource.setSendInfo(sendInfoRequestDto)
        }.onSuccess {
            Timber.d("onboarding setSendInfo Impl 성공")
        }.onFailure {
            Timber.e("onboarding setSendInfo Impl 성공")
        }

    override suspend fun setReceiveInfo(receiveInfoRequestDto: ReceiveInfoRequestDto): Result<ReceiveInfoResponseDto> =
        runCatching {
            onboardingRemoteDataSource.setReceiveInfo(receiveInfoRequestDto)
        }.onSuccess {
            Timber.d("onboarding setReceiveInfo Impl 성공")
        }.onFailure {
            Timber.e("onboarding setReceiveInfo Impl 성공")
        }
}