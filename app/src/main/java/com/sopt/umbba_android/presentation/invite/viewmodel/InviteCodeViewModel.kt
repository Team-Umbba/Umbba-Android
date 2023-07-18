package com.sopt.umbba_android.presentation.invite.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.umbba_android.data.model.request.InviteCodeRequestDto
import com.sopt.umbba_android.data.model.response.LoginResponseDto
import com.sopt.umbba_android.data.repository.LoginRepositoryImpl
import com.sopt.umbba_android.data.repository.OnboardingRepositoryImpl
import kotlinx.coroutines.launch

class InviteCodeViewModel(private val onboardingRepositoryImpl: OnboardingRepositoryImpl) : ViewModel() {
    val code = MutableLiveData<String>()

    val isCodeValidate = MutableLiveData<Boolean>()

    private val _isCodeSuccess: MutableLiveData<Boolean> = MutableLiveData()
    val isCodeSuccess: LiveData<Boolean>
        get() = _isCodeSuccess

    fun checkCodeComplete() {
        isCodeValidate.value = !code.value.isNullOrEmpty()
    }

    fun setFamily(inviteCode: String) {
        viewModelScope.launch {
            onboardingRepositoryImpl.setFamily(
                InviteCodeRequestDto(inviteCode)
            ).onSuccess {
                Log.e("yeonjin", "Set Family 성공")
                _isCodeSuccess.value = true
            }.onFailure { error ->
                Log.e("yeonjin", "Set Family 실패 " + error.message + error)
                _isCodeSuccess.value = false
            }
        }
    }
}