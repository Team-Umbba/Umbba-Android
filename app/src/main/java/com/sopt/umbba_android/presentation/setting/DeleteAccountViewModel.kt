package com.sopt.umbba_android.presentation.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.umbba_android.data.repository.SettingRepositoryImpl
import kotlinx.coroutines.launch

class DeleteAccountViewModel(private val settingRepositoryImpl: SettingRepositoryImpl) :
    ViewModel() {
    fun deleteAccount() {
        viewModelScope.launch {
            settingRepositoryImpl.deleteAccount()
        }
    }
}