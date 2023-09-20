package com.ubcompany.umbba_android.presentation.onboarding.quest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ubcompany.umbba_android.data.model.request.ReceiveInfoRequestDto
import com.ubcompany.umbba_android.data.repository.OnboardingRepositoryImpl
import com.ubcompany.umbba_android.domain.entity.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
@HiltViewModel
class QuestViewModel(private val onboardingRepositoryImpl: OnboardingRepositoryImpl) : ViewModel() {
    val isClickedYes = MutableLiveData<Boolean>()
    val isClickedNo = MutableLiveData<Boolean>()
    val isClickedAmbiguous = MutableLiveData<Boolean>()
    val isClickedComplete = MutableLiveData<Boolean>()
    val clickedChipText = MutableLiveData<String>()
    val notifyTime = MutableLiveData<String>()

    private val _isPostSuccess: MutableLiveData<Boolean> = MutableLiveData()
    val isPostSuccess: LiveData<Boolean>
        get() = _isPostSuccess

    fun checkButtonComplete() {
        isClickedComplete.value =
            isClickedYes.value == true || isClickedNo.value == true || isClickedAmbiguous.value == true
    }

    fun setReceiveInfo(info: User?, quest: List<String>) {
        viewModelScope.launch {
            onboardingRepositoryImpl.setReceiveInfo(
                ReceiveInfoRequestDto(
                    ReceiveInfoRequestDto.UserInfoData(
                        name = info?.name!!,
                        gender = info.gender!!,
                        bornYear = info.bornYear!!
                    ),
                    onboardingAnswerList = quest
                )
            ).onSuccess {
                notifyTime.value = it.data.pushTime
                _isPostSuccess.value = true
                Timber.d("setReceiveInfo 성공")
            }.onFailure {
                _isPostSuccess.value = false
                Timber.e("setReceiveInfo 실패")
            }
        }
    }

}