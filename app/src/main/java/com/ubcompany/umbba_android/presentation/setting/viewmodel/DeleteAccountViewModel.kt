package com.ubcompany.umbba_android.presentation.setting.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ubcompany.umbba_android.data.repository.SettingRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
@HiltViewModel
class DeleteAccountViewModel(private val settingRepositoryImpl: SettingRepositoryImpl) :
    ViewModel() {
    var responseStatus = MutableLiveData<Int>()
    fun signout() {
        viewModelScope.launch {
            settingRepositoryImpl.signout()
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