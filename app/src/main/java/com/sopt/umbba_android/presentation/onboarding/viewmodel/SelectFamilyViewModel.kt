package com.sopt.umbba_android.presentation.onboarding.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SelectFamilyViewModel : ViewModel() {
    val parent = MutableLiveData<Boolean>()
    val child = MutableLiveData<Boolean>()
    val mom = MutableLiveData<Boolean>()
    val dad = MutableLiveData<Boolean>()

    val isAllSelectFamily = MutableLiveData<Boolean>()

    fun checkSelectFamily() {
        isAllSelectFamily.value = (parent.value == true || child.value == true) && (mom.value == true || dad.value == true)
    }
}