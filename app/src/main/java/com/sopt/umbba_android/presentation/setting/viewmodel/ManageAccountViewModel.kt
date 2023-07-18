package com.sopt.umbba_android.presentation.setting.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.umbba_android.data.repository.SettingRepositoryImpl
import kotlinx.coroutines.launch

class ManageAccountViewModel(private val settingRepositoryImpl: SettingRepositoryImpl) :
    ViewModel() {

    var responseStatus = MutableLiveData<Int>()
    fun logout(): Int? {
        viewModelScope.launch {
            settingRepositoryImpl.logout()
                .onSuccess { response ->
                    Log.e("hyeon", "viewmodel logout 성공")
                    responseStatus.value = response.status
                }.onFailure { error ->
                    Log.e("hyeon", "viewmodel logout 실패 " + error.message)
                    responseStatus.value = -1
                }
        }
        return responseStatus.value
    }
}