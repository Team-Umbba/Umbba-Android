package com.ubcompany.umbba_android.presentation.invite.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ubcompany.umbba_android.data.model.request.InviteCodeRequestDto
import com.ubcompany.umbba_android.data.repository.OnboardingRepositoryImpl
import com.ubcompany.umbba_android.domain.repository.OnboardingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class InviteCodeViewModel @Inject constructor(private val onboardingRepository: OnboardingRepository) :
    ViewModel() {
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
            onboardingRepository.setFamily(
                InviteCodeRequestDto(inviteCode)
            ).onSuccess {
                Timber.d("Set Family 성공")
                _isCodeSuccess.value = true
            }.onFailure { error ->
                Timber.e("Set Family 실패 $error")
                _isCodeSuccess.value = false
            }
        }
    }
}