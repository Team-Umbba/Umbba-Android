package com.ubcompany.umbba_android.presentation.setting.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ubcompany.umbba_android.data.repository.SettingRepositoryImpl
import kotlinx.coroutines.launch
import timber.log.Timber

class ManageAccountViewModel(private val settingRepositoryImpl: SettingRepositoryImpl) :
    ViewModel() {

    var responseStatus = MutableLiveData<Int>()
    fun logout(): Int? {
        viewModelScope.launch {
            settingRepositoryImpl.logout()
                .onSuccess { response ->
                    responseStatus.value = response.status
                    Timber.d("viewmodel logout 성공")
                }.onFailure { error ->
                    responseStatus.value = -1
                    Timber.e("viewmodel logout 실패 $error")
                }
        }
        return responseStatus.value
    }
}