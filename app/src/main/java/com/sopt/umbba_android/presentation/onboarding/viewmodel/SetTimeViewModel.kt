package com.sopt.umbba_android.presentation.onboarding.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.umbba_android.data.model.request.SendInfoRequestDto
import com.sopt.umbba_android.data.repository.OnboardingRepositoryImpl
import com.sopt.umbba_android.domain.entity.User
import kotlinx.coroutines.launch
import timber.log.Timber

class SetTimeViewModel(private val onboardingRepositoryImpl: OnboardingRepositoryImpl): ViewModel() {

    private val _isPostSuccess: MutableLiveData<Boolean> = MutableLiveData()
    val isPostSuccess: LiveData<Boolean>
        get() = _isPostSuccess

    fun setSendInfo(info: User?, pushTime: String, quest: List<String>) {
        viewModelScope.launch {
            onboardingRepositoryImpl.setSendInfo(
                SendInfoRequestDto(
                    SendInfoRequestDto.SenderData(name = info?.name!!, gender = info.gender!!, bornYear = info.bornYear!!),
                    isInvitorChild = info.isInvitorChild!!,
                    relationInfo = info.relationInfo!!,
                    pushTime = pushTime,
                    onboardingAnswerList = quest
                )
            ).onSuccess {
                _isPostSuccess.value = true
                Timber.d("setSendInfo 성공")

            }.onFailure { error ->
                _isPostSuccess.value = false
                Timber.e("setSendInfo 실패 $error")
            }
        }
    }
}