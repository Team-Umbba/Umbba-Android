package com.sopt.umbba_android.presentation.onboarding

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class InputInfoViewModel : ViewModel() {
    val name = MutableLiveData<String>()
    val man = MutableLiveData<Boolean>()
    val woman = MutableLiveData<Boolean>()
    val year = MutableLiveData<String>()

    val isAllInfoComplete = MutableLiveData<Boolean>()

    fun checkInfoComplete() {
        return if (!name.value.isNullOrEmpty() && !year.value.isNullOrEmpty() && ((man.value == true) || (woman.value == true))) {
            isAllInfoComplete.value = true
        } else {
            isAllInfoComplete.value = false
        }
    }
}