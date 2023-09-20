package com.ubcompany.umbba_android.presentation.setting.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ubcompany.umbba_android.data.repository.SettingRepositoryImpl
import com.ubcompany.umbba_android.domain.repository.SettingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DeleteAccountViewModel @Inject constructor(private val settingRepository: SettingRepository) :
    ViewModel() {
    var responseStatus = MutableLiveData<Int>()
    fun signout() {
        viewModelScope.launch {
            settingRepository.signout()
                .onSuccess { response ->
                    responseStatus.value = response.status
                    Timber.d("viewModel 회원탈퇴 성공")
                }.onFailure { error ->
                    responseStatus.value = -1
                    Timber.e("viewModel 회원탈퇴 실패 $error")
                }
        }
    }
}