package com.sopt.umbba_android.presentation.setting.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.umbba_android.data.repository.SettingRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

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