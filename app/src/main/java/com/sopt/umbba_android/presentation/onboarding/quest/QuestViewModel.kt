package com.sopt.umbba_android.presentation.onboarding.quest

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.umbba_android.data.model.request.ReceiveInfoRequestDto
import com.sopt.umbba_android.data.repository.OnboardingRepositoryImpl
import com.sopt.umbba_android.domain.entity.User
import kotlinx.coroutines.launch

class QuestViewModel(private val onboardingRepositoryImpl: OnboardingRepositoryImpl) : ViewModel() {
    val isClickedYes = MutableLiveData<Boolean>()
    val isClickedNo = MutableLiveData<Boolean>()
    val isClickedAmbiguous = MutableLiveData<Boolean>()

    val isClickedComplete = MutableLiveData<Boolean>()

    val clickedChipText = MutableLiveData<String>()

    private val _isPostSuccess: MutableLiveData<Boolean> = MutableLiveData()
    val isPostSuccess: LiveData<Boolean>
        get() = _isPostSuccess

    val notifyTime = MutableLiveData<String>()

    fun checkButtonComplete() {
        isClickedComplete.value = isClickedYes.value == true || isClickedNo.value == true || isClickedAmbiguous.value == true
    }

    fun setReceiveInfo(info: User?, quest: List<String>) {
        viewModelScope.launch {
            onboardingRepositoryImpl.setReceiveInfo(
                ReceiveInfoRequestDto(
                    ReceiveInfoRequestDto.UserInfoData(name = info?.name!!, gender = info.gender!!, bornYear = info.bornYear!!),
                    onboardingAnswerList = quest
                )
            ).onSuccess {
                Log.e("yeonjin", "setReceiveInfo 성공")
                _isPostSuccess.value = true
                notifyTime.value = it.data.pushTime
            }.onFailure {
                Log.e("yeonjin", "setReceiveInfo 실패")
                _isPostSuccess.value = false
            }
        }
    }

}