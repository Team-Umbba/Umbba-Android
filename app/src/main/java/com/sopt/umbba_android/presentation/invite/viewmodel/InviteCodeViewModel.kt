package com.sopt.umbba_android.presentation.invite.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class InviteCodeViewModel : ViewModel() {
    val code = MutableLiveData<String>()

    val isCodeValidate = MutableLiveData<Boolean>()

    fun checkCodeComplete() {
        isCodeValidate.value = !code.value.isNullOrEmpty()
    }
}