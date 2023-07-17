package com.sopt.umbba_android.presentation.setting.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.umbba_android.data.repository.SettingRepositoryImpl
import kotlinx.coroutines.launch

class ManageAccountViewModel(private val settingRepositoryImpl: SettingRepositoryImpl) :
    ViewModel() {
    fun logout() {
        viewModelScope.launch {
            settingRepositoryImpl.logout()
        }
    }
}